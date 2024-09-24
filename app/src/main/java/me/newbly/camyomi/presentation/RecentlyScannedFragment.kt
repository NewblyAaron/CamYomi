package me.newbly.camyomi.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.newbly.camyomi.databinding.FragmentRecentlyScannedBinding

class RecentlyScannedFragment : Fragment() {
    private var _binding: FragmentRecentlyScannedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyScannedBinding.inflate(inflater, container, false)

        return binding.root
    }
}