package me.newbly.camyomi.presentation.contract

import android.graphics.Bitmap
import me.newbly.camyomi.domain.entity.DictionaryEntry

interface OCRScannerContract {
    interface View {
        fun launchImagePicker()
        fun launchCamera()
        fun displayProgress()
        fun hideProgress()
        fun toggleFabMenu()
        fun showRecognizedText(wordMap: Map<String, String>)
        fun showDefinitions(entries: List<DictionaryEntry>)
        fun showError(errorMessage: String)
    }

    interface Presenter {
        fun onScanFabClicked()
        fun onCameraButtonClicked()
        fun onImagePickerButtonClicked()
        fun onImageCaptured(image: Bitmap)
        fun onTextClicked(text: String)
    }
}