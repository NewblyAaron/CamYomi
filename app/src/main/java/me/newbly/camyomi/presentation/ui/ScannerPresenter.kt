package me.newbly.camyomi.presentation.ui

import android.graphics.Bitmap
import android.util.Log
import com.atilika.kuromoji.TokenizerBase
import com.atilika.kuromoji.ipadic.Tokenizer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import me.newbly.camyomi.domain.usecase.AddBookmarkUseCase
import me.newbly.camyomi.domain.usecase.FetchDefinitionsUseCase
import me.newbly.camyomi.domain.usecase.RecognizeTextUseCase
import me.newbly.camyomi.domain.usecase.SaveToRecentlyScannedUseCase
import me.newbly.camyomi.presentation.contract.ScannerContract

class ScannerPresenter @AssistedInject constructor(
    @Assisted private val view: ScannerContract.View,
    private val recognizeTextUseCase: RecognizeTextUseCase,
    private val fetchDefinitionsUseCase: FetchDefinitionsUseCase,
    private val saveToRecentlyScannedUseCase: SaveToRecentlyScannedUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase
) : ScannerContract.Presenter {

    private val presenterScope = CoroutineScope(Dispatchers.Main)
    private val tokenizer = Tokenizer.Builder().mode(TokenizerBase.Mode.SEARCH).build()

    @AssistedFactory
    interface Factory {
        fun create(view: ScannerContract.View): ScannerPresenter
    }

    override fun onScanFabClicked() = view.toggleFabMenu()
    override fun onCameraButtonClicked() = view.launchCamera()
    override fun onImagePickerButtonClicked() = view.launchImagePicker()
    override suspend fun onBookmarkButtonClicked(dictionaryEntryId: Int): Boolean = saveNewBookmark(dictionaryEntryId)
    override suspend fun onImageCaptured(image: Bitmap) = processTextRecognition(image)
    override suspend fun onTextClicked(selectedText: String) = getDefinitionsFor(selectedText)
    override suspend fun loadPassedArgs(passedText: String) = processRecentScan(passedText)

    private suspend fun getRecognizedText(image: Bitmap) =
        presenterScope.async(Dispatchers.IO) {
            return@async recognizeTextUseCase.invoke(image)
        }

    private suspend fun saveToRecentlyScanned(scannedText: String) =
        presenterScope.async(Dispatchers.IO) {
            return@async saveToRecentlyScannedUseCase.invoke(scannedText)
        }

    private suspend fun fetchDefinitions(word: String) =
        presenterScope.async(Dispatchers.IO) {
            return@async fetchDefinitionsUseCase.invoke(word)
        }

    private suspend fun addBookmark(dictionaryEntryId: Int) =
        presenterScope.async(Dispatchers.IO) {
            return@async addBookmarkUseCase.invoke(dictionaryEntryId)
        }

    private fun tokenizeText(text: String) =
        presenterScope.async(Dispatchers.IO) {
            val tokens = tokenizer.tokenize(text)
            val wordMap = mutableMapOf<String, String>()
            var log = ""
            tokens.forEach {
                log += "${it.surface}\t${it.allFeatures}\n"
                wordMap[it.surface] = it.baseForm
            }
            Log.d("OCRScannerPresenter", log)

            return@async wordMap
        }

    private suspend fun processTextRecognition(image: Bitmap) {
        try {
            view.displayRecognizeProgress()

            val recognizedText = getRecognizedText(image).await().getOrThrow()
            saveToRecentlyScanned(recognizedText).await().getOrThrow()
            val wordMap = tokenizeText(recognizedText).await()

            view.hideRecognizeProgress()
            view.toggleFabMenu()            // hide the fab menu
            view.showDefinitions(listOf())  // clear definitions list
            view.showRecognizedText(wordMap)
        } catch (e: Exception) {
            view.hideRecognizeProgress()
            handleException(e)
        }
    }

    private suspend fun processRecentScan(recentScanText: String) {
        try {
            view.displayRecognizeProgress()

            val wordMap = tokenizeText(recentScanText).await()

            view.hideRecognizeProgress()
            view.showDefinitions(listOf())  // clear definitions list
            view.showRecognizedText(wordMap)
        } catch (e: Exception) {
            view.hideRecognizeProgress()
            handleException(e)
        }
    }

    private suspend fun getDefinitionsFor(word: String) {
        try {
            view.displayDefinitionsProgress()

            val definitions = fetchDefinitions(word).await().getOrThrow()

            view.hideDefinitionsProgress()
            view.showDefinitions(definitions)
        } catch (e: Exception) {
            view.hideDefinitionsProgress()
            handleException(e)
        }
    }

    private suspend fun saveNewBookmark(dictionaryEntryId: Int): Boolean {
        try {
            return addBookmark(dictionaryEntryId).await().getOrThrow()
        } catch (e: Exception) {
            handleException(e)
            return false
        }
    }

    private fun handleException(e: Exception) {
        e.message?.let { view.showError(it) }
    }
}