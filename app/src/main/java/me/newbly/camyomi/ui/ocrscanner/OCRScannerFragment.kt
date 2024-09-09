package me.newbly.camyomi.ui.ocrscanner

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import me.newbly.camyomi.databinding.FragmentOcrScannerBinding
import me.newbly.camyomi.mvp.OCRScannerContract
import javax.inject.Inject

class OCRScannerFragment : Fragment(), OCRScannerContract.View {

    @Inject lateinit var presenter: OCRScannerPresenter
    private lateinit var binding: FragmentOcrScannerBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
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