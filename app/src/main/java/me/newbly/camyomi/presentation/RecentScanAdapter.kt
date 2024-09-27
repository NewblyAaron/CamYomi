package me.newbly.camyomi.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.newbly.camyomi.databinding.ScannedItemBinding
import me.newbly.camyomi.domain.entity.RecentScan

class RecentScanAdapter: ListAdapter<RecentScan, RecentScanAdapter.ViewHolder>(DIFF_CALLBACK) {
    private var onListItemClick: ((RecentScan) -> Unit)? = null

    class ViewHolder(
        private val binding: ScannedItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(recentScan: RecentScan) {
            binding.scannedText.text = recentScan.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ScannedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repoItem = getItem(position)

        holder.bindItem(repoItem)
        holder.itemView.setOnClickListener { onListItemClick?.invoke(repoItem) }
    }

    fun setOnListItemClickListener(l: (RecentScan) -> Unit) {
        onListItemClick = l
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<RecentScan> = object: DiffUtil.ItemCallback<RecentScan>() {
            override fun areItemsTheSame(oldItem: RecentScan, newItem: RecentScan): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RecentScan, newItem: RecentScan): Boolean =
                oldItem == newItem
        }
    }
}