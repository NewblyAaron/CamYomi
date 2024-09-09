package me.newbly.camyomi.ui.ocrscanner

import me.newbly.camyomi.mvp.BasePresenter
import me.newbly.camyomi.mvp.OCRScannerContract

class OCRScannerPresenter(
//    private val model: OCRScannerContract.Model,
    override val view: OCRScannerContract.View,
) : BasePresenter<OCRScannerContract.View>(view), OCRScannerContract.Presenter {
    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun onCameraSelected() {
        TODO("Not yet implemented")
    }

    override fun onImagePickerSelected() {
        TODO("Not yet implemented")
    }

    override fun onImageCaptured(image: Any) {
        TODO("Not yet implemented")
    }

    override fun onOCRResult(text: String) {
        TODO("Not yet implemented")
    }

    override fun onOCRFailure(e: Exception) {
        TODO("Not yet implemented")
    }

    override fun fetchDefinitions(text: String) {
        TODO("Not yet implemented")
    }

    override fun onButtonClicked() {
        view.showHello()
    }
}