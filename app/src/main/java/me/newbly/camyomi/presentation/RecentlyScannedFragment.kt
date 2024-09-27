package me.newbly.camyomi.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import me.newbly.camyomi.databinding.FragmentRecentlyScannedBinding
import me.newbly.camyomi.domain.entity.RecentScan
import me.newbly.camyomi.presentation.contract.RecentlyScannedContract
import javax.inject.Inject

@AndroidEntryPoint
class RecentlyScannedFragment : Fragment(), RecentlyScannedContract.View {

    @Inject
    lateinit var presenterFactory: RecentlyScannedPresenter.Factory
    private lateinit var presenter: RecentlyScannedContract.Presenter

    private var _binding: FragmentRecentlyScannedBinding? = null
    private val binding get() = _binding!!

    private val recentScanAdapter = RecentScanAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = presenterFactory.create(this)
        presenter.getRecentScans()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyScannedBinding.inflate(inflater, container, false)
        binding.bindView()

        return binding.root
    }

    override fun navigateToScanner(queryText: String) {
        val action = RecentlyScannedFragmentDirections.actionGlobalNavigationScanner()
        findNavController().previousBackStackEntry?.savedStateHandle?.set("recentText", queryText)
        findNavController().navigate(action)
    }

    override fun showRecentScans(recentScans: List<RecentScan>) {
        recentScanAdapter.submitList(recentScans)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(
            context,
            "Error! $errorMessage",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun FragmentRecentlyScannedBinding.bindView() {
        recentScanAdapter.setOnListItemClickListener { presenter.onRecentScanClicked(it) }
        recentScanList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentScanAdapter
        }
        recentScanList.addItemDecoration(DividerItemDecoration(recentScanList.context, RecyclerView.VERTICAL))
    }
}