package com.android.dang.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ItemRecyclerViewRecentWordBinding

class RecentAdapter : RecyclerView.Adapter<RecentAdapter.Holder>() {

    private var recentWord = mutableListOf<String>("aksfd", "slkava")

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    private var itemClick: ItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentAdapter.Holder {
        val binding = ItemRecyclerViewRecentWordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecentAdapter.Holder, position: Int) {
        holder.recentText.text = recentWord[position]
    }

    override fun getItemCount(): Int {
        return recentWord.size
    }

    inner class Holder(binding: ItemRecyclerViewRecentWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recentText = binding.recentText
    }
}