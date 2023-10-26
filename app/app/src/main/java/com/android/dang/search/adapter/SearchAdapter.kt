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

    var itemClick: ItemClick? = null

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

        val address = currentItem.careAddr
        val parts = address.split(" ")
        val result = "#${parts[0]} ${parts[1]}"

        Glide.with(holder.itemView.context)
            .load(currentItem.popfile)
            .into(holder.image)
        val text2 = currentItem.kindCd
        val result2 = text2.replace("[개] ", "")
        holder.dogKind.text = result2
        var text1 = "#${currentItem.age}"
        text1 += result
        text1 += "#${currentItem.processState}"
        text1 += when (currentItem.sexCd) {
            "M" -> "#수컷"
            "F" -> "#암컷"
            else -> "#미상"
        }
        text1 += when (currentItem.neuterYn){
            "Y" -> "#중성화"
            "N" -> ""
            else -> "#미상"
        }
        text1 += "#${currentItem.weight}"
        text1 += "\n#${currentItem.specialMark}"
        holder.age.text = text1

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
        searchesList.clear()
        searchesList.addAll(list)
        notifyDataSetChanged()
    }
}