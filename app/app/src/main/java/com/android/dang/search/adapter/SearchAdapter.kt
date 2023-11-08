package com.android.dang.search.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.R
import com.android.dang.databinding.ItemCommonDetailBinding
import com.android.dang.databinding.ItemRecyclerViewRecentWordBinding
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.util.PrefManager
import com.bumptech.glide.Glide


class SearchAdapter(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var searchesList = mutableListOf<SearchDogData>()
    private var recentList = mutableListOf<String>()

    private var likeList = PrefManager.getLikeItem(mContext)

    interface ItemClick {
        fun onClick(view: View, position: Int)
        fun onImageViewClick(position: Int)
        fun onTextViewClick(position: Int)

        fun onLikeViewClick(position: Int)
    }

    var itemClick: ItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return typeOne
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val binding = ItemCommonDetailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SearchHolder(binding)
            }
            1 -> {
                val binding = ItemRecyclerViewRecentWordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RecentWordHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (typeOne) {
            0 -> {
                holder.itemView.setOnClickListener {
                    itemClick?.onClick(it, position)
                }

                val currentItem = searchesList[position]

                val searchHolder = holder as SearchHolder

                val address = currentItem.careAddr
                val parts = address?.split(" ")
                val result = "#${parts?.get(0)} ${parts?.get(1)}"

                Glide.with(searchHolder.itemView.context)
                    .load(currentItem.popfile)
                    .into(searchHolder.image)

                val text2 = currentItem.kindCd
                val result2 = text2?.replace("[개] ", "")
                searchHolder.dogKind.text = result2

                var text1 = "#${currentItem.age}"
                text1 += result
                text1 += "#${currentItem.processState}"
                text1 += when (currentItem.sexCd) {
                    "M" -> "#수컷"
                    "F" -> "#암컷"
                    else -> "#미상"
                }
                text1 += when (currentItem.neuterYn) {
                    "Y" -> "#중성화"
                    "N" -> ""
                    else -> "#미상"
                }
                text1 += "#${currentItem.weight}"
                text1 += "\n#${currentItem.specialMark}"
                searchHolder.age.text = text1

                for (list in likeList){
                    if (currentItem.popfile == list.popfile){
                        holder.like.setImageResource(R.drawable.icon_like_on)
                        currentItem.isLiked = true
                        break
                    } else {
                        holder.like.setImageResource(R.drawable.icon_like_off)
                        currentItem.isLiked = false
                    }
                }
                if (likeList.isEmpty()){
                    holder.like.setImageResource(R.drawable.icon_like_off)
                    currentItem.isLiked = false
                }
                holder.like.setOnClickListener {
                    itemClick?.onLikeViewClick(position)
                    if (currentItem.isLiked == true) {
                        holder.like.setImageResource(R.drawable.icon_like_on)
                        Log.d("test1", "${currentItem.isLiked}")
                    } else {
                        holder.like.setImageResource(R.drawable.icon_like_off)
                        Log.d("test2", "${currentItem.isLiked}")
                    }
                }
            }
            1 -> {
                val recentWordHolder = holder as RecentWordHolder
                recentWordHolder.recentText.text = recentList[position]
            }
        }
    }

    override fun getItemCount(): Int {
        return when (typeOne) {
            0 -> searchesList.size
            1 -> recentList.size
            else -> 0
        }
    }

    inner class SearchHolder(binding: ItemCommonDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.dogImg
        val dogKind = binding.dogName
        val age = binding.dogTag
        val like = binding.dogLike

    }

    inner class RecentWordHolder(binding: ItemRecyclerViewRecentWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recentText = binding.recentText
        private val cancel = binding.recentCancel

        init {
            cancel.setOnClickListener {
                itemClick?.onImageViewClick(adapterPosition)
            }
            recentText.setOnClickListener {
                itemClick?.onTextViewClick(adapterPosition)
            }
        }
    }

    fun searchesData(list: List<SearchDogData>) {
        searchesList.clear()
        searchesList.addAll(list)
        notifyDataSetChanged()
    }

    fun recentData(list: List<String>) {
        recentList.clear()
        recentList.addAll(list)
        notifyDataSetChanged()
    }

    fun searchNew(){
        likeList = PrefManager.getLikeItem(mContext)
        notifyDataSetChanged()
    }

    companion object{
        var typeOne = 1
    }
}
