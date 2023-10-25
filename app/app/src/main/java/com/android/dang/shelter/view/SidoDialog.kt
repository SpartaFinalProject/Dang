package com.android.dang.shelter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dang.databinding.DialogSidoBinding
import com.android.dang.retrofit.sido.Sido
import java.security.PrivateKey

class SidoDialog(
    private val sidoList: List<Sido>,
    private val onClickSido: (Sido) -> Unit
): DialogFragment() {
    private lateinit var binding: DialogSidoBinding
    private lateinit var adapter: SidoRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSidoBinding.inflate(inflater)
        adapter = SidoRecyclerViewAdapter(sidoList) {
            onClickSido(it)
            dismiss()
        }
        setRecyclerView()
        return binding.root
    }


    private fun setRecyclerView() {
        binding.recyclerviewSido.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewSido.adapter = adapter
    }
}