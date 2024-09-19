package me.newbly.camyomi.ui.ocrscanner

import android.graphics.Bitmap
import android.util.Log
import com.atilika.kuromoji.ipadic.Tokenizer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.newbly.camyomi.mvp.BasePresenter
import me.newbly.camyomi.mvp.OCRScannerContract

class OCRScannerPresenter @AssistedInject constructor(
    private val model: OCRScannerContract.Model,
    @Assisted override val view: OCRScannerContract.View,
) : BasePresenter<OCRScannerContract.View>(view), OCRScannerContract.Presenter {

    private val presenterScope = CoroutineScope(Dispatchers.Main)
    private val tokenizer = Tokenizer()

    @AssistedFactory
    interface Factory {
        fun create(view: OCRScannerContract.View): OCRScannerPresenter
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
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
        model.processImageForOCR(
            image,
            onSuccess = { result ->
                onOCRResult(result)
            },
            onFailure = { exception ->
                onOCRFailure(exception)
            }
        )
    }

    private fun onOCRResult(text: String) {
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
        Log.e("OCRScannerPresenter", e.localizedMessage, e)
        e.localizedMessage?.let { view.showError(it) }
    }

    override fun onTextClicked(text: String) {
        getEntryOf(text)
    }

    private fun getEntryOf(text: String) {
        presenterScope.launch {
            model.getEntries(
                text,
                onSuccess = { entries ->
                    view.showDefinitions(entries)
                },
                onFailure = { e ->
                    Log.e("OCRScannerPresenter", e.localizedMessage, e)
                    e.localizedMessage?.let { view.showError(it) }
                }
            )
        }
    }
}