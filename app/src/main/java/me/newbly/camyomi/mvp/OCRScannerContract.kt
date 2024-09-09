package me.newbly.camyomi.mvp

import com.google.mlkit.vision.common.InputImage

interface OCRScannerContract {
    interface View {
        fun launchImagePicker()
        fun launchCamera()
        fun displayProgress()
        fun hideProgress()
        fun showResult(text: String, definitions: Map<String, String>)
        fun showError(errorMessage: String)
    }

    interface Presenter {
        fun onCameraSelected()
        fun onImagePickerSelected()
        fun onImageCaptured(image: Any)
        fun onOCRResult(text: String)
        fun onOCRFailure(e: Exception)
        fun fetchDefinitions(text: String)
    }

    interface Model {
        fun processImageForOCR(
            image: Any,
            onSuccess: (String) -> Unit,
            onFailure: (Exception) -> Unit
        )
        fun getDefinitions(
            text: String,
            onSuccess: (Map<String, String>) -> Unit,
            onFailure: (Exception) -> Unit
        )
    }
}