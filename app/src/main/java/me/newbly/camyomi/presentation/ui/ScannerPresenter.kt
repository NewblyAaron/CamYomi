package me.newbly.camyomi.presentation.ui

import android.graphics.Bitmap
import android.util.Log
import com.atilika.kuromoji.TokenizerBase
import com.atilika.kuromoji.ipadic.Tokenizer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.newbly.camyomi.domain.entity.Word
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
    @AssistedFactory
    interface Factory {
        fun create(view: ScannerContract.View): ScannerPresenter
    }

    private val tokenizer = Tokenizer.Builder().mode(TokenizerBase.Mode.SEARCH).build()

    override fun onScanFabClicked() = view.toggleFabMenu()
    override fun onCameraButtonClicked() = view.launchCamera()
    override fun onImagePickerButtonClicked() = view.launchImagePicker()
    override suspend fun onBookmarkButtonClicked(dictionaryEntryId: Int): Boolean =
        saveNewBookmark(dictionaryEntryId)
    override suspend fun onImageCaptured(image: Bitmap) = processTextRecognition(image)
    override suspend fun onWordSelected(selectedText: String) = getDefinitionsFor(selectedText)
    override suspend fun loadPassedArgs(passedText: String) = processRecentScan(passedText)

    private suspend fun processTextRecognition(image: Bitmap) {
        try {
            view.displayRecognizeProgress()

            val recognizedText = recognizeTextUseCase(image).getOrThrow()
            saveToRecentlyScannedUseCase(recognizedText).getOrThrow()
            val wordMap = tokenizeText(recognizedText)

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

            val wordMap = tokenizeText(recentScanText)

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

            val definitions = fetchDefinitionsUseCase(word).getOrThrow()

            view.hideDefinitionsProgress()
            view.showDefinitions(definitions)
        } catch (e: Exception) {
            view.hideDefinitionsProgress()
            handleException(e)
        }
    }

    private suspend fun saveNewBookmark(dictionaryEntryId: Int): Boolean {
        try {
            return addBookmarkUseCase(dictionaryEntryId).getOrThrow()
        } catch (e: Exception) {
            handleException(e)
            return false
        }
    }

    private suspend fun tokenizeText(text: String): List<Word> =
        withContext(Dispatchers.IO) {
            val tokens = tokenizer.tokenize(text)
            val words = mutableListOf<Word>()
            tokens.forEach {
                words.add(Word(
                    originalForm = it.surface,
                    baseForm = it.baseForm
                ))
            }

            return@withContext words
        }

    private fun handleException(e: Exception) {
        e.message?.let { view.showError(it) }
    }
}