package com.android.dang.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.FragmentHomeBinding
import com.android.dang.home.homeAdapter.HomeAdapter
import com.android.dang.home.retrofit.HomeData
import com.android.dang.home.retrofit.HomeItemModel
import com.android.dang.home.retrofit.RetrofitClient.apiService
import com.android.dang.home.retrofit.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(){
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private var resItems: ArrayList<HomeItemModel> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentHomeBinding.inflate(layoutInflater)

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        recyclerView = binding.homeRc
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = HomeAdapter(mContext)
        recyclerView.adapter = adapter


        adapter.clearItem()
        homeResult()
        return binding.root
    }

    private fun homeResult(){
        apiService.homeDang(
            Util.KEY,10,"json"
        )
            .enqueue(object : Callback<HomeData?> {
                override fun onResponse(call: Call<HomeData?>, response: Response<HomeData?>) {
                    if (response.isSuccessful) {
                        val homeData = response.body()
                        homeData?.response?.body?.items?.item?.forEach { item ->
                            val filename = item.filename
                            val kindCd = item.kindcd
                            val age = item.age
                            val specialMark = item.specialmark
                            val happenPlace = item.happenplace
                            resItems.add(HomeItemModel(filename, kindCd, age, specialMark, happenPlace))

                            Log.d("js", "$resItems")

                        }
                    }
                    else {
                        Log.e("error", "${response.code()}")
                }
                    adapter.items = resItems
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<HomeData?>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                }
            })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}