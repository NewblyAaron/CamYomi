package me.newbly.camyomi.ui.ocrscanner

import android.graphics.Bitmap
import android.util.Log
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import me.newbly.camyomi.mvp.BasePresenter
import me.newbly.camyomi.mvp.OCRScannerContract

class OCRScannerPresenter @AssistedInject constructor(
    private val model: OCRScannerContract.Model,
    @Assisted override val view: OCRScannerContract.View,
) : BasePresenter<OCRScannerContract.View>(view), OCRScannerContract.Presenter {
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
        Log.d("OCRScannerPresenter", "Recognized text: $text")
    }

    override fun onOCRFailure(e: Exception) {
        Log.e("OCRScannerPresenter", e.localizedMessage, e)
    }

    override fun fetchDefinitions(text: String) {
        TODO("Not yet implemented")
    }
}