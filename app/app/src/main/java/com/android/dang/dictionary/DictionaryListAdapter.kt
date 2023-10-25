package com.android.dang.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ItemDictionaryBinding
import com.android.dang.dictionary.retrofit.data.BreedsData

class DictionaryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ItemClick {
        fun onClick(position: Int)
    }

    var itemClick: ItemClick? = null
    private val items = ArrayList<BreedsData.BreedsDataItem>()
    private var spinner: Spinner? = null
    private var expandedPosition: Int = -1
    fun setSpinner(spinner: Spinner) {
        this.spinner = spinner
    }
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
        val videoItemViewHolder = (holder as DictionaryListAdapter.DictionaryItemViewHolder)
        videoItemViewHolder.bind(item)
        videoItemViewHolder.itemView.setOnClickListener {
            itemClick?.onClick(position)
        }
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