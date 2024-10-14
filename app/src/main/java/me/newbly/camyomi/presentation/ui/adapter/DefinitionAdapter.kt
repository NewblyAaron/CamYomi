package me.newbly.camyomi.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.newbly.camyomi.R
import me.newbly.camyomi.databinding.DefinitionItemBinding
import me.newbly.camyomi.domain.entity.DictionaryEntry

class DefinitionAdapter(
    private val areBookmarks: Boolean = false
) : ListAdapter<DictionaryEntry, DefinitionAdapter.ViewHolder>(DIFF_CALLBACK) {
    private var onBookmarkButtonClick: ((DictionaryEntry) -> Unit)? = null

    inner class ViewHolder(
        private val binding: DefinitionItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(dictionaryEntry: DictionaryEntry) {
            binding.kanjiReadingText.text = dictionaryEntry.getMainKanjiReading()
            binding.kanaReadingText.text = dictionaryEntry.getMainKanaReading()
            binding.otherReadingsText.text = dictionaryEntry.getOtherReadings()
            binding.glossaryText.text = dictionaryEntry.getGlossary()

            binding.bookmarkButton.setOnClickListener {
                onBookmarkButtonClick?.invoke(
                    dictionaryEntry
                )
            }
            updateBookmarkIcon(dictionaryEntry.isBookmarked)
        }

        private fun updateBookmarkIcon(isBookmarked: Boolean) =
            when (areBookmarks) {
                true -> {
                    binding.bookmarkButton.setImageResource(
                        R.drawable.baseline_bookmark_remove_24
                    )
                }

                else -> {
                    when (isBookmarked) {
                        true -> {
                            binding.bookmarkButton.isEnabled = false
                            binding.bookmarkButton.setImageResource(
                                R.drawable.baseline_bookmark_added_24
                            )
                        }

                        false -> {
                            binding.bookmarkButton.isEnabled = true
                            binding.bookmarkButton.setImageResource(R.drawable.baseline_bookmark_add_24)
                        }
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DefinitionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repoItem = getItem(position)

        holder.bindItem(repoItem)
    }

    fun setBookmarkButtonOnClickListener(l: (DictionaryEntry) -> Unit) {
        onBookmarkButtonClick = l
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<DictionaryEntry> =
            object : DiffUtil.ItemCallback<DictionaryEntry>() {
                override fun areItemsTheSame(
                    oldItem: DictionaryEntry,
                    newItem: DictionaryEntry
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: DictionaryEntry,
                    newItem: DictionaryEntry
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
