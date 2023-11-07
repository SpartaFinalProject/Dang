package com.android.dang.shelter.shelterresult

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ItemCommonDetailBinding
import com.android.dang.like.LikeAdapter
import com.android.dang.retrofit.abandonedDog.AbandonedShelter

class ShelterResultAdapter(
    private val onClickDog: (AbandonedShelter) -> Unit
): RecyclerView.Adapter<DogItemViewHolder>() {
    private var shelterList = mutableListOf<AbandonedShelter>()

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private var clickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clickListener = listener
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

        holder.setItem(item)
        holder.itemView.setOnClickListener {
            onClickDog(item)
        }
    }

    fun addAll(list: List<AbandonedShelter>) {
        shelterList = list.toMutableList()
        notifyDataSetChanged()
    }
}