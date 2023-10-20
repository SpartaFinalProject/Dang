package com.android.dang.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ItemCommonDetailBinding
import com.android.dang.search.searchItemModel.SearchDogData
import com.bumptech.glide.Glide


class SearchAdapter : RecyclerView.Adapter<SearchAdapter.Holder>() {

    private var searchesList = mutableListOf<SearchDogData>()

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    private var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.Holder {
        val binding = ItemCommonDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        val currentItem = searchesList[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.filename)
            .into(holder.image)
        holder.dogKind.text = currentItem.kindCd
        var text1 = "#${currentItem.age}"
        text1 += "#${currentItem.careAddr}"
        text1 += "#${currentItem.processState}"
        holder.age.text = text1
        var text2 = "#${currentItem.sexCd}"
        text2 += "#${currentItem.neuterYn}"
        text2 += "#${currentItem.weight}"
//        holder.sexCd.text = text2
//        holder.details.text = currentItem.specialMark
        Log.d("recyclerView", searchesList.size.toString())
    }

    override fun getItemCount(): Int {
        return searchesList.size
    }

    inner class Holder(binding: ItemCommonDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.dogImg
        val dogKind = binding.dogName
        val age = binding.dogTag
    }

    fun searchesData(list: List<SearchDogData>) {
        Log.d("aaa", list.toString())
        searchesList.clear()
        searchesList.addAll(list)
        Log.d("aaaa", searchesList.toString())
        notifyDataSetChanged()
    }
}