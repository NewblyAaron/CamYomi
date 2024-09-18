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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.newbly.camyomi.ui.adapter.DefinitionAdapter
import me.newbly.camyomi.database.entity.Entry
import me.newbly.camyomi.databinding.FragmentOcrScannerBinding
import me.newbly.camyomi.mvp.OCRScannerContract
import javax.inject.Inject

@AndroidEntryPoint
class OCRScannerFragment : Fragment(), OCRScannerContract.View {

    @Inject lateinit var presenterFactory: OCRScannerPresenter.Factory
    private lateinit var presenter: OCRScannerContract.Presenter

    private var _binding: FragmentOcrScannerBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var pickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private val definitionAdapter = DefinitionAdapter()
    private var recognizedTextMap = mapOf<String, String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = presenterFactory.create(this)

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOcrScannerBinding.inflate(layoutInflater)
        binding.bindView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            if (hasCameraPermission()) {
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

    override fun showDefinitions(entries: List<Entry>) {
        definitionAdapter.submitList(entries)
    }

    override fun showRecognizedText(wordMap: Map<String, String>) {
        recognizedTextMap = wordMap
        binding.composeView.setContent {
            RecognizedJapaneseText(recognizedTextMap)
        }
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(
            context,
            "Error in processing image!\n$errorMessage",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun FragmentOcrScannerBinding.bindView() {
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecognizedJapaneseText(recognizedTextMap)
            }
        }

        definitionList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = definitionAdapter
        }

        launchCameraButton.setOnClickListener { presenter.onCameraSelected() }
        launchPickerButton.setOnClickListener { presenter.onImagePickerSelected() }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun RecognizedJapaneseText(
        map: Map<String, String>,
    ) {
        val selectedText: MutableState<String> = rememberSaveable { mutableStateOf("") }
        val color = if (isSystemInDarkTheme()) Color.White else Color.Black

        MaterialTheme {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (map.isEmpty()) {
                    Text("Recognized text will display here", color = color)
                } else {
                    for (word in map.entries) {
                        val style = if (word.key == selectedText.value) {
                            MaterialTheme.typography.bodyLarge.copy(
                                color = color,
                                background = Color.Red
                            )
                        } else {
                            MaterialTheme.typography.bodyLarge.copy(
                                color = color
                            )
                        }

                        Text(
                            word.key,
                            style = style,
                            modifier = Modifier
                                .clickable {
                                    selectedText.value = word.key
                                    presenter.onTextClicked(word.value)
                                }
                        )
                    }
                }
            }
        }
    }
}