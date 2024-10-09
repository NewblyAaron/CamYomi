package me.newbly.camyomi.presentation.contract

import android.graphics.Bitmap
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.domain.entity.Word

interface ScannerContract {
    interface View {
        fun launchImagePicker()
        fun launchCamera()
        fun displayRecognizeProgress()
        fun displayDefinitionsProgress()
        fun hideRecognizeProgress()
        fun hideDefinitionsProgress()
        fun toggleFabMenu()
        fun showRecognizedText(words: List<Word>)
        fun showDefinitions(entries: List<DictionaryEntry>)
        fun showError(errorMessage: String)
    }

    interface Presenter {
        fun onScanFabClicked()
        fun onCameraButtonClicked()
        fun onImagePickerButtonClicked()
        suspend fun onBookmarkButtonClicked(dictionaryEntryId: Int): Boolean
        suspend fun onImageCaptured(image: Bitmap)
        suspend fun onWordSelected(selectedText: String)
        suspend fun loadPassedArgs(passedText: String)
    }
}
