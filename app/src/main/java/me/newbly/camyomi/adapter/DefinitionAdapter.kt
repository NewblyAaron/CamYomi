package me.newbly.camyomi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.newbly.camyomi.database.entity.Entry
import me.newbly.camyomi.databinding.DefinitionItemBinding

class DefinitionAdapter : ListAdapter<Entry, DefinitionAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(
        private val binding: DefinitionItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(entry: Entry) {
            binding.kanjiReadingText.text = entry.keb
            binding.kanaReadingText.text = entry.re
            binding.glossaryText.text = entry.gloss
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DefinitionItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repoItem = getItem(position)

        holder.bindItem(repoItem)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Entry> = object: DiffUtil.ItemCallback<Entry>() {
            override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
                return oldItem == newItem
            }
        }
    }
}