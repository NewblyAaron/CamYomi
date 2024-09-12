package me.newbly.camyomi.ui.ocrscanner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import me.newbly.camyomi.database.entity.Entry
import me.newbly.camyomi.databinding.FragmentOcrScannerBinding
import me.newbly.camyomi.mvp.OCRScannerContract
import javax.inject.Inject

@AndroidEntryPoint
class OCRScannerFragment : Fragment(), OCRScannerContract.View {

    @Inject lateinit var presenterFactory: OCRScannerPresenter.Factory
    private lateinit var presenter: OCRScannerContract.Presenter
    private lateinit var binding: FragmentOcrScannerBinding
    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var pickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = presenterFactory.create(this)
        binding = FragmentOcrScannerBinding.inflate(layoutInflater)

        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                presenter.onImageCaptured(bitmap)
            }
        }
        pickerLauncher = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                val bitmap = ImageDecoder.decodeBitmap(source)

                presenter.onImageCaptured(bitmap)
            } else {
                Toast.makeText(
                    context,
                    "No image selected!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (!it) {
                Toast.makeText(
                    context,
                    "Go to settings and enable camera permission to use this feature",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                launchCamera()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.launchCameraButton.setOnClickListener { presenter.onCameraSelected() }
        binding.launchPickerButton.setOnClickListener { presenter.onImagePickerSelected() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun launchImagePicker() {
        try {
            pickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } catch (e: Exception) {
            Log.e("OCRScannerFragment", e.localizedMessage, e)
        }
    }

    override fun launchCamera() {
        try {
            if (checkCameraPermissions()) {
                cameraLauncher.launch(null)
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        } catch (e: Exception) {
            Log.e("OCRScannerFragment", e.localizedMessage, e)
        }
    }

    override fun displayProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun showEntry(entry: Entry) {
        TODO("Not yet implemented")
    }

    override fun showRecognizedText(text: String) {
        binding.recognizedText.text = text
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(
            context,
            "Error in processing image!\n$errorMessage",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkCameraPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED
    }
}