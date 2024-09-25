package me.newbly.camyomi.presentation

import android.graphics.Bitmap
import android.util.Log
import com.atilika.kuromoji.TokenizerBase
import com.atilika.kuromoji.ipadic.Tokenizer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.newbly.camyomi.domain.usecase.FetchDefinitionsUseCase
import me.newbly.camyomi.domain.usecase.GetRecognizedTextUseCase
import me.newbly.camyomi.domain.usecase.SaveToRecentlyScannedUseCase
import me.newbly.camyomi.presentation.contract.ScannerContract

class ScannerPresenter @AssistedInject constructor(
    @Assisted private val view: ScannerContract.View,
    private val getRecognizedTextUseCase: GetRecognizedTextUseCase,
    private val fetchDefinitionsUseCase: FetchDefinitionsUseCase,
    private val saveToRecentlyScannedUseCase: SaveToRecentlyScannedUseCase
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

    override fun onImageCaptured(image: Bitmap) {
        presenterScope.launch {
            try {
                val result = getRecognizedTextUseCase.invoke(image).getOrThrow()
                onOCRResult(result)
            } catch (e: Exception) {
                onOCRFailure(e)
            }
        }
    }

    override fun onTextClicked(text: String) {
        getDefinitionsOfText(text)
    }

    private suspend fun onOCRResult(text: String) {
        saveToRecentlyScannedUseCase.invoke(text)

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

    private fun onOCRFailure(e: Exception) {
        e.message?.let { view.showError(it) }
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