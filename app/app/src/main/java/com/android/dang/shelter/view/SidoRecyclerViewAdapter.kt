package com.android.dang.shelter.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.android.dang.databinding.ItemSidoBinding
import com.android.dang.retrofit.sido.Sido

class SidoRecyclerViewAdapter(
    private val sidoList: List<Sido>,
    private val onClickSido: (Sido) -> Unit
): Adapter<SidoRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SidoRecyclerViewHolder {
        val binding = ItemSidoBinding.inflate(LayoutInflater.from(parent.context))
        return SidoRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = sidoList.size

    override fun onBindViewHolder(holder: SidoRecyclerViewHolder, position: Int) {
        val sido = sidoList[holder.adapterPosition]
        holder.run {
            setSidoTitle(sido.orgdownNm)
            itemView.setOnClickListener {
                onClickSido(sido)
            }
        }
    }
}