package me.newbly.camyomi.ui.ocrscanner

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import me.newbly.camyomi.mvp.BasePresenter
import me.newbly.camyomi.mvp.OCRScannerContract
import javax.inject.Inject

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