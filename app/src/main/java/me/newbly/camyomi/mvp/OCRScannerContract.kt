package me.newbly.camyomi.mvp

import android.graphics.Bitmap
import me.newbly.camyomi.database.entity.Entry

interface OCRScannerContract {
    interface View {
        fun launchImagePicker()
        fun launchCamera()
        fun displayProgress()
        fun hideProgress()
        fun showRecognizedText(wordMap: Map<String, String>)
        fun showDefinitions(entries: List<Entry>)
        fun showError(errorMessage: String)
    }

    interface Presenter {
        fun onCameraSelected()
        fun onImagePickerSelected()
        fun onImageCaptured(image: Bitmap)
        fun onTextClicked(text: String)
    }

    interface Model {
        fun processImageForOCR(
            image: Bitmap,
            onSuccess: (String) -> Unit,
            onFailure: (Exception) -> Unit
        )
        suspend fun getEntries(
            text: String,
            onSuccess: (List<Entry>) -> Unit,
            onFailure: (Exception) -> Unit
        )
    }
}