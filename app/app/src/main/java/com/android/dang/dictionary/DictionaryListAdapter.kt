package com.android.dang.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ItemDictionaryBinding
import com.android.dang.dictionary.data.BreedsData

class DictionaryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<BreedsData.BreedsDataItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryItemViewHolder {
        return DictionaryItemViewHolder(
            ItemDictionaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val dictionaryItemViewHolder = (holder as DictionaryListAdapter.DictionaryItemViewHolder)
        dictionaryItemViewHolder.bind(item)

    }

    fun addItems(resData: ArrayList<BreedsData.BreedsDataItem>, isClear: Boolean) {
        if(isClear) items.clear()

        items.addAll(resData)
        notifyDataSetChanged()
    }

    inner class DictionaryItemViewHolder(val binding: ItemDictionaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BreedsData.BreedsDataItem) {
            binding.item = item
        }
    }
}