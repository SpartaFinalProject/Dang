package com.android.dang.like

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.FragmentLikeBinding
import com.android.dang.home.retrofit.HomeItemModel
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.util.PrefManager


class LikeFragment : Fragment() {
    private var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private lateinit var adapter: LikeAdapter
    private lateinit var recyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentLikeBinding.inflate(layoutInflater)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)

        val likeItems = PrefManager.getLikeItem(mContext)
        Log.d("LikeFragment", "itmes.size = ${likeItems.size}")

        recyclerView = binding.likeRc
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = LikeAdapter(mContext)
        adapter.items = likeItems
        recyclerView.adapter = adapter


        adapter.setOnItemClickListener(object : LikeAdapter.OnItemClickListener {
            override fun onItemClick(item: SearchDogData, position: Int) {

            }
        })
        ItemTouchHelper(Swipe(adapter)).attachToRecyclerView(binding.likeRc)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}