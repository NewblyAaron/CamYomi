package me.newbly.camyomi.ui.ocrscanner

import android.graphics.Bitmap
import android.util.Log
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

    override fun onCameraSelected() {
        view.launchCamera()
    }

    override fun onImagePickerSelected() {
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

    override fun onOCRResult(text: String) {
        view.showRecognizedText(text)

        getEntryOf(text)
    }

    override fun onOCRFailure(e: Exception) {
        Log.e("OCRScannerPresenter", e.localizedMessage, e)
        e.localizedMessage?.let { view.showError(it) }
    }

    private fun getEntryOf(text: String) {
        presenterScope.launch {
            model.getEntries(
                text,
                onSuccess = {
                    Log.d("OCRScannerPresenter", it.toString())
                },
                onFailure = {
                    Log.e("OCRScannerPresenter", it.localizedMessage, it)
                }
            )
        }
    }
}