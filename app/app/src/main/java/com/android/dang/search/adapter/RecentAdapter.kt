package com.android.dang.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ItemRecyclerViewRecentWordBinding

class RecentAdapter : RecyclerView.Adapter<RecentAdapter.Holder>() {

    private var recentList = mutableListOf<String>()

    interface ItemClick {
        fun onImageViewClick(position: Int)
        fun onTextViewClick(position: Int)
    }

    var itemClick: ItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentAdapter.Holder {
        val binding = ItemRecyclerViewRecentWordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecentAdapter.Holder, position: Int) {
        holder.recentText.text = recentList[position]
    }

    override fun getItemCount(): Int {
        return recentList.size
    }

    inner class Holder(binding: ItemRecyclerViewRecentWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recentText = binding.recentText
        val cancel = binding.recentCancel

        init {
            cancel.setOnClickListener {
                itemClick?.onImageViewClick(adapterPosition)
            }
            recentText.setOnClickListener {
                itemClick?.onTextViewClick(adapterPosition)
            }
        }
    }

    fun recentData(list: List<String>) {
        recentList.clear()
        recentList.addAll(list)
        notifyDataSetChanged()
    }
}