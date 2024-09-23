package me.newbly.camyomi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.databinding.DefinitionItemBinding

class DefinitionAdapter : ListAdapter<DictionaryEntry, DefinitionAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(
        private val binding: DefinitionItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(dictionaryEntry: DictionaryEntry) {
            binding.kanjiReadingText.text = dictionaryEntry.getMainKanjiReading()
            binding.kanaReadingText.text = dictionaryEntry.getMainKanaReading()
            binding.otherReadingsText.text = dictionaryEntry.getOtherReadings()
            binding.glossaryText.text = dictionaryEntry.getGlossary()
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

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<DictionaryEntry> = object: DiffUtil.ItemCallback<DictionaryEntry>() {
            override fun areItemsTheSame(oldItem: DictionaryEntry, newItem: DictionaryEntry): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DictionaryEntry, newItem: DictionaryEntry): Boolean {
                return oldItem == newItem
            }
        }
    }
}