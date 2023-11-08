package com.android.dang.shelter.shelterresult

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.R
import com.android.dang.databinding.ItemCommonDetailBinding
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.util.PrefManager
import com.bumptech.glide.Glide

class ShelterResultAdapter(private val mContext: Context
): RecyclerView.Adapter<ShelterResultAdapter.DogItemViewHolder>() {
    private var shelterList = mutableListOf<SearchDogData>()
    private var likeList = PrefManager.getLikeItem(mContext)

    interface ItemClick {
        fun onClick(view: View, position: Int)

        fun onLikeViewClick(position: Int)
    }

    var itemClick: ItemClick? = null




    private var clickListener: ItemClick? = null

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogItemViewHolder {
        val binding = ItemCommonDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DogItemViewHolder(binding)
    }

    override fun getItemCount(): Int = shelterList.size

    override fun onBindViewHolder(holder: DogItemViewHolder, position: Int) {

        val item = shelterList[position]

        holder.itemView.setOnClickListener{
            itemClick?.onClick(it, position)
        }

        val address = item.careAddr
        val parts = address?.split(" ")
        val result = "#${parts?.get(0)} ${parts?.get(1)}"
        Glide.with(mContext)
            .load(
                item.popfile
                    ?.toUri()
                    ?.buildUpon()
                    ?.scheme("https")
                    ?.build()
            ).into(holder.dogImg)
        val modifiedKindCd = item.kindCd?.replace("[개]", "")?.trim() ?: ""
        holder.dogName.text = modifiedKindCd

        val processText = ellipsizeText(
            item.age,
            result,
            item.processState,
            item.sexCd,
            item.neuterYn,
            item.weight,
            item.specialMark,
            70
        )
        holder.dogTag.text = processText

        for (list in likeList){
            if (item.popfile == list.popfile){
                holder.dogLike.setImageResource(R.drawable.icon_like_on)
                item.isLiked = true
                break
            } else {
                holder.dogLike.setImageResource(R.drawable.icon_like_off)
                item.isLiked = false
            }
        }
        if (likeList.isEmpty()){
            holder.dogLike.setImageResource(R.drawable.icon_like_off)
            item.isLiked = false
        }
        holder.dogLike.setOnClickListener {
            itemClick?.onLikeViewClick(position)
            if (item.isLiked == true) {
                holder.dogLike.setImageResource(R.drawable.icon_like_on)
            } else {
                holder.dogLike.setImageResource(R.drawable.icon_like_off)
            }
        }

    }

    inner class DogItemViewHolder(binding: ItemCommonDetailBinding) :
        RecyclerView.ViewHolder(binding.root),View.OnClickListener {

        var dogImg: ImageView = binding.dogImg
        var dogName: TextView = binding.dogName
        var dogTag: TextView = binding.dogTag
        var dogLike: ImageView = binding.dogLike

        init {
            dogLike.setOnClickListener(this)
        }
        override fun onClick(view: View?) {
            view?.let {}
        }
    }
    fun searchNew(){
        likeList = PrefManager.getLikeItem(mContext)
        notifyDataSetChanged()
    }


    fun addAll(list: List<SearchDogData>) {
        shelterList = list.toMutableList()
        notifyDataSetChanged()
    }
}