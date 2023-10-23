package com.android.dang.home.homeAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.MainActivity
import com.android.dang.R
import com.android.dang.databinding.ItemCommonDetailBinding
import com.android.dang.home.retrofit.HomeItemModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

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
            .load(currentItem.popfile)
            .into(holder.dogImg)
        holder.dogName.text = currentItem.kindCd
        var text = "#${currentItem.age}"
        text += "#${currentItem.specialMark}"
        text += "#${currentItem.orgNm}"
        text += "#${currentItem.processState}"
        holder.dogTag.text = text

        if (currentItem.isLiked) {
            holder.dogLike.setImageResource(R.drawable.icon_like_on)
        } else {
            holder.dogLike.setImageResource(R.drawable.icon_like_off)
        }
        holder.dogLike.setOnClickListener {
            currentItem.isLiked = !currentItem.isLiked
            if (currentItem.isLiked) {
                holder.dogLike.setImageResource(R.drawable.icon_like_on)
            } else {
                holder.dogLike.setImageResource(R.drawable.icon_like_off)
            }
        }
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
        var dogLike: ImageView = binding.dogLike
        var dogBox: ConstraintLayout = binding.dogBox


        init {
            dogImg.setOnClickListener(this)
            dogBox.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            view?.let {

            }
        }
    }
}
