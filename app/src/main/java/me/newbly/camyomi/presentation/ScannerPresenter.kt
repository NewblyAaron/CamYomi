package me.newbly.camyomi.presentation

import android.database.sqlite.SQLiteException
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.newbly.camyomi.domain.usecase.FetchDefinitionsUseCase
import me.newbly.camyomi.domain.usecase.RecognizeTextUseCase
import me.newbly.camyomi.domain.usecase.AddBookmarkUseCase
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

    override fun onScanFabClicked() {
        view.toggleFabMenu()
    }

    override fun onCameraButtonClicked() {
        view.launchCamera()
    }

    override fun onImagePickerButtonClicked() {
        view.launchImagePicker()
    }

    override suspend fun onBookmarkButtonClicked(dictionaryEntryId: Int): Boolean =
        presenterScope.async {
            try {
                when (addBookmarkUseCase.invoke(dictionaryEntryId).getOrThrow()) {
                    false -> throw SQLiteException("Failed to insert data")
                    else -> { return@async true }
                }
            } catch (e: Exception) {
                e.message?.let { view.showError(it) }
                return@async false
            }
        }.await()

    override fun onImageCaptured(image: Bitmap) {
        presenterScope.launch {
            try {
                val result = recognizeTextUseCase.invoke(image).getOrThrow()
                saveToRecentlyScannedUseCase.invoke(result)
                tokenizeText(result)
            } catch (e: Exception) {
                e.message?.let { view.showError(it) }
            }
        }
    }

    override fun onTextClicked(selectedText: String) {
        getDefinitionsOfText(selectedText)
    }

    override fun loadPassedArgs(passedText: String) {
        presenterScope.launch {
            tokenizeText(passedText)
        }
    }

    private suspend fun tokenizeText(text: String) {
        withContext(Dispatchers.IO) {
            val tokens = tokenizer.tokenize(text)
            val wordMap = mutableMapOf<String, String>()
            var log = ""
            tokens.forEach {
                log += "${it.surface}\t${it.allFeatures}\n"
                wordMap[it.surface] = it.baseForm
            }
            Log.d("OCRScannerPresenter", log)

            view.showRecognizedText(wordMap)
        }
    }

    private fun getDefinitionsOfText(queryText: String) {
        presenterScope.launch {
            try {
                val result = fetchDefinitionsUseCase.invoke(queryText)

                view.showDefinitions(result.getOrThrow())
            } catch (e: Exception) {
                e.message?.let { view.showError(it) }
            }
        }
    }
}