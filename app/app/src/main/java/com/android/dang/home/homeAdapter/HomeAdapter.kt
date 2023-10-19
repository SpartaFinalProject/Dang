package com.android.dang.home.homeAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ItemCommonDetailBinding
import com.android.dang.home.retrofit.HomeItemModel
import com.bumptech.glide.Glide

class HomeAdapter(private val mContext: Context) :
    RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {
    var items = ArrayList<HomeItemModel>()

    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ItemViewHolder {
        val binding =
            ItemCommonDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        Glide.with(mContext)
            .load(currentItem.filename)
            .into(holder.dogImg)
        holder.dogName.text = currentItem.kindCd
        holder.dogTag.text = currentItem.age


    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(binding: ItemCommonDetailBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var dogImg: ImageView = binding.dogImg
        var dogName: TextView = binding.dogName
        var dogTag: TextView = binding.dogTag

        init {
            dogImg.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }
    }


}