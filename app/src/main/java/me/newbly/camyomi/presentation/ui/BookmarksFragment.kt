package me.newbly.camyomi.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.newbly.camyomi.databinding.FragmentBookmarksBinding
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.presentation.contract.BookmarksContract
import me.newbly.camyomi.presentation.ui.adapter.DefinitionAdapter
import javax.inject.Inject

@AndroidEntryPoint
class BookmarksFragment : Fragment(), BookmarksContract.View {

    @Inject
    lateinit var presenterFactory: BookmarksPresenter.Factory
    private lateinit var presenter: BookmarksContract.Presenter

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    private val bookmarksAdapter = DefinitionAdapter(areBookmarks = true)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = presenterFactory.create(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showBookmarkedDefinitions(entries: List<DictionaryEntry>) {
        bookmarksAdapter.submitList(entries)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(
            context,
            "Error! $errorMessage",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun displayProgress() {
        binding.bookmarksProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.bookmarksProgressBar.visibility = View.GONE
    }

    private fun FragmentBookmarksBinding.bindView() {
        bookmarksAdapter.setBookmarkButtonOnClickListener {
            removeBookmark(it)
        }
        bookmarkList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookmarksAdapter
        }
        bookmarkList.addItemDecoration(
            DividerItemDecoration(
                bookmarkList.context,
                RecyclerView.VERTICAL
            )
        )

        lifecycleScope.launch {
            presenter.getBookmarks()
        }
    }

    private fun removeBookmark(it: DictionaryEntry) {
        lifecycleScope.launch {
            val result = presenter.onBookmarkButtonClicked(it.id)
            if (result) {
                val position = bookmarksAdapter.currentList.indexOf(it)
                val newList = bookmarksAdapter.currentList.toMutableList()
                newList.removeAt(position)
                bookmarksAdapter.submitList(newList)
            }
        }
    }

}