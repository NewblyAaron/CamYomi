package me.newbly.camyomi.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.newbly.camyomi.databinding.FragmentScannerBinding
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.domain.entity.Word
import me.newbly.camyomi.presentation.contract.ScannerContract
import me.newbly.camyomi.presentation.ui.adapter.DefinitionAdapter
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ScannerFragment : Fragment(), ScannerContract.View {

    @Inject
    lateinit var presenterFactory: ScannerPresenter.Factory
    private lateinit var presenter: ScannerContract.Presenter

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    // need to suppress as the camera launcher input type is explicitly java.lang.Void?
    @Suppress("ForbiddenVoid")
    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var pickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private val definitionAdapter = DefinitionAdapter()
    private var recognizedWords = listOf<Word>()

    private var isFabExtended = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = presenterFactory.create(this)

        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            if (bitmap != null) {
                lifecycleScope.launch {
                    presenter.onImageCaptured(bitmap)
                }
            }
        }
        pickerLauncher = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                val bitmap = ImageDecoder.decodeBitmap(source)

                lifecycleScope.launch {
                    presenter.onImageCaptured(bitmap)
                }
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        binding.bindView()

        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>("recentText")
            ?.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    lifecycleScope.launch { presenter.loadPassedArgs(it) }
                }
            }

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
            handleException(e)
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
            handleException(e)
        }
    }

    override fun displayRecognizeProgress() {
        binding.recognizeProgressBar.visibility = View.VISIBLE
    }

    override fun displayDefinitionsProgress() {
        binding.definitionsProgressBar.visibility = View.VISIBLE
    }

    override fun hideRecognizeProgress() {
        binding.recognizeProgressBar.visibility = View.GONE
    }

    override fun hideDefinitionsProgress() {
        binding.definitionsProgressBar.visibility = View.GONE
    }

    override fun toggleFabMenu() {
        isFabExtended = when {
            isFabExtended -> {
                binding.hideFabMenu()
                false
            }

            !isFabExtended -> {
                binding.showFabMenu()
                true
            }

            else -> {
                error("that's not supposed to happen! booleans can't be a \"maybe\"")
            }
        }
    }

    override fun showRecognizedText(words: List<Word>) {
        recognizedWords = words
        binding.composeView.setContent {
            RecognizedJapaneseText(recognizedWords)
        }
    }

    override fun showEditDialog() {
        val currentText = recognizedWords.joinToString(separator = "") {
            it.originalForm
        }
        val dialog = EditDialogFragment(
            oldText = currentText
        )
        dialog.setOnDialogPositiveButtonClickListener {
            lifecycleScope.launch {
                presenter.loadPassedArgs(it)
            }
        }

        dialog.show(parentFragmentManager, "EDIT_DIALOG")
    }

    override fun showDefinitions(entries: List<DictionaryEntry>) {
        definitionAdapter.submitList(entries)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(
            context,
            "Error! $errorMessage",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun FragmentScannerBinding.bindView() {
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecognizedJapaneseText(recognizedWords)
            }
        }

        definitionAdapter.setBookmarkButtonOnClickListener {
            lifecycleScope.launch {
                it.isBookmarked = presenter.onBookmarkButtonClicked(it.id)
                definitionAdapter.notifyItemChanged(definitionAdapter.currentList.indexOf(it))
            }
        }
        definitionList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = definitionAdapter
        }
        definitionList.addItemDecoration(
            DividerItemDecoration(
                definitionList.context,
                RecyclerView.VERTICAL
            )
        )

        hideRecognizeProgress()
        hideDefinitionsProgress()
        hideFabMenu()

        scanFab.setOnClickListener { presenter.onScanFabClicked() }
        launchCameraButton.setOnClickListener { presenter.onCameraButtonClicked() }
        launchPickerButton.setOnClickListener { presenter.onImagePickerButtonClicked() }
        editButton.setOnClickListener { presenter.onEditButtonClicked() }
    }

    private fun FragmentScannerBinding.hideFabMenu() {
        launchCameraButton.hide()
        launchPickerButton.hide()
        cameraText.visibility = View.GONE
        pickerText.visibility = View.GONE
        scanFab.shrink()
    }

    private fun FragmentScannerBinding.showFabMenu() {
        launchCameraButton.show()
        launchPickerButton.show()
        cameraText.visibility = View.VISIBLE
        pickerText.visibility = View.VISIBLE
        scanFab.extend()
    }

    private fun hasCameraPermission(): Boolean = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA,
    ) == PackageManager.PERMISSION_GRANTED

    private fun handleException(e: Exception) = Timber.e(e)

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun RecognizedJapaneseText(
        words: List<Word> = recognizedWords,
    ) {
        val selectedText: MutableState<String> = rememberSaveable { mutableStateOf("") }
        val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        val fontSize = 32.sp

        MaterialTheme {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (words.isEmpty()) {
                    Text(
                        "Recognized text will display here",
                        color = textColor,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                } else {
                    for (word in words) {
                        Text(
                            word.originalForm,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (selectedText.value == word.originalForm) {
                                    Color.White
                                } else {
                                    textColor
                                },
                                fontSize = fontSize,
                            ),
                            modifier = Modifier
                                .clickable {
                                    selectedText.value = word.originalForm

                                    lifecycleScope.launch {
                                        presenter.onWordSelected(word.baseForm)
                                    }
                                }
                                .background(
                                    color = if (selectedText.value == word.originalForm) {
                                        Color.Red
                                    } else {
                                        Color.Transparent
                                    }
                                )
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    @Suppress("UnusedPrivateMember")
    private fun ComposeViewPreview() {
        RecognizedJapaneseText(
            words = listOf(
                Word("今日", "今日"),
                Word("行こう", "行く"),
                Word("かな", "かな"),
            )
        )
    }
}
