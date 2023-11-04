package com.android.dang.like

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.R
import com.android.dang.databinding.ItemCommonDetailBinding
import com.android.dang.search.LikeList
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.util.PrefManager.addItem
import com.android.dang.util.PrefManager.deleteItem
import com.bumptech.glide.Glide

class LikeAdapter(private val mContext: Context) :
    RecyclerView.Adapter<LikeAdapter.ItemViewHolder>() {
    var items = mutableListOf<SearchDogData>()

    interface OnItemClickListener {
        fun onItemClick(item: SearchDogData, position: Int)
    }

    private var clickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeAdapter.ItemViewHolder {
        val binding =
            ItemCommonDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikeAdapter.ItemViewHolder, position: Int) {

        val currentItem = items[position]

        val address = currentItem.careAddr
        val parts = address.split(" ")
        val result = "#${parts[0]} ${parts[1]}"

        Glide.with(mContext)
            .load(currentItem.popfile)
            .into(holder.dogImg)
        val modifiedKindCd = currentItem.kindCd?.replace("[개]", "")?.trim() ?: ""
        holder.dogName.text = modifiedKindCd

        val processText = ellipsizeText(currentItem.age, result,currentItem.processState,currentItem.sexCd, currentItem.neuterYn, currentItem.weight, currentItem.specialMark, 70)
        holder.dogTag.text = processText


        holder.dogLike.setOnClickListener {
            clickListener?.onItemClick(items[position], position)
        }
        if (currentItem.isLiked) {
            holder.dogLike.setImageResource(R.drawable.icon_like_on)
        } else {
            holder.dogLike.setImageResource(R.drawable.icon_like_off)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(binding: ItemCommonDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var dogImg: ImageView = binding.dogImg
        var dogName: TextView = binding.dogName
        var dogTag: TextView = binding.dogTag
        var dogLike: ImageView = binding.dogLike
        var dogBox: ConstraintLayout = binding.dogBox

    }

    private fun ellipsizeText(
        age: String?,
        careAddr: String?,
        processState: String?,
        sexCd: String?,
        neuterYn: String?,
        weight: String?,
        specialMark: String?,
        maxLength: Int
    ): String {
        val sexText = when (sexCd){
            "M" -> "수컷"
            "F" -> "암컷"
            else -> "미상"
        }

        val neuter = when (neuterYn){
            "Y" -> "중성화"
            "N" -> ""
            else -> "미상"
        }
        val ellipstext =
            "#${age ?: ""} ${careAddr ?: ""} #${processState ?: ""} #${sexText ?: ""} #${neuter ?: ""}#${weight ?: ""} \n#${specialMark ?: ""} "
        return ellipstext.ellipsize(maxLength)
    }

    private fun String.ellipsize(maxLength: Int): String {
        return if (length > maxLength) {
            "${substring(0, maxLength - 3)}..."
        } else {
            this
        }
    }

    fun addAll(newItems: List<SearchDogData>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    fun insertData(position: Int, item: SearchDogData) {
        items.add(position,item)
        notifyItemInserted(position)
        addItem(mContext,items[position])
    }

    fun data(position: Int) : SearchDogData{
        Log.d("item", "items.size: ${items.size}")
       return items[position]
    }

    fun removeData(position: Int) {
        LikeList.deleteLikeList(items[position])
        Log.d("list30", "${LikeList.likeList.size}")
        try {
            items[position].popfile?.let { deleteItem(mContext, it) }
            items.removeAt(position)
            notifyItemRemoved(position)

        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }
}