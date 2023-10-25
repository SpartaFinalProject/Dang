package com.android.dang.shelter.view

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.dang.databinding.ItemSidoBinding

class SidoRecyclerViewHolder(private val binding: ItemSidoBinding): ViewHolder(binding.root) {
    fun setSidoTitle(title: String) {
        binding.txtSido.text = title
    }
}