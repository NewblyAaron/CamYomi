package me.newbly.camyomi.ui.ocrscanner

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import me.newbly.camyomi.databinding.FragmentOcrScannerBinding
import me.newbly.camyomi.mvp.OCRScannerContract
import javax.inject.Inject

@AndroidEntryPoint
class OCRScannerFragment : Fragment(), OCRScannerContract.View {

    @Inject lateinit var presenterFactory: OCRScannerPresenter.Factory
    private lateinit var presenter: OCRScannerContract.Presenter
    private lateinit var binding: FragmentOcrScannerBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = presenterFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentOcrScannerBinding.inflate(layoutInflater)
        binding.helloButton.setOnClickListener{ presenter.onButtonClicked() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun launchImagePicker() {
        TODO("Not yet implemented")
    }

    override fun launchCamera() {
        TODO("Not yet implemented")
    }

    override fun displayProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun showResult(text: String, definitions: Map<String, String>) {
        TODO("Not yet implemented")
    }

    override fun showError(errorMessage: String) {
        TODO("Not yet implemented")
    }

    override fun showHello() {
        var helloText = binding.helloText
        when (helloText.text) {
            "Hi!" -> helloText.text = "Hello!"
            else -> helloText.text = "Hi!"
        }
    }
}