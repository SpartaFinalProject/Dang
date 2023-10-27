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
import com.android.dang.R
import com.android.dang.databinding.BottomNavigationBinding
import com.android.dang.databinding.FragmentHomeBinding
import com.android.dang.home.homeAdapter.HomeAdapter
import com.android.dang.home.retrofit.HomeData
import com.android.dang.home.retrofit.HomeItemModel
import com.android.dang.home.retrofit.RetrofitClient.apiService
import com.android.dang.home.retrofit.Util
import com.android.dang.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
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

//        intentSearch()

        // shelter 바꿔야뎀!!
        binding.bannerMoreBtn.setOnClickListener {
            val shelterFragment = SearchFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_view, shelterFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        recyclerView = binding.homeRc
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = HomeAdapter(mContext)
        recyclerView.adapter = adapter

        adapter.clearItem()
        homeResult()
        return binding.root
    }

    private fun homeResult() {
        apiService.homeDang(
            Util.KEY, 50, "json", 417000
        )
            .enqueue(object : Callback<HomeData?> {
                override fun onResponse(call: Call<HomeData?>, response: Response<HomeData?>) {
                    if (response.isSuccessful) {
                        val homeData = response.body()
                        homeData?.response?.body?.items?.item?.forEach { item ->
                            val popfile = item.popfile
                            val kindCd = item.kindCd
                            val age = item.age
                            val specialMark = item.specialMark
                            val happenPlace = item.happenPlace
                            val orgNm = item.orgNm
                            val processState = item.processState
                            resItems.add(
                                HomeItemModel(
                                    popfile,
                                    kindCd,
                                    age,
                                    specialMark,
                                    happenPlace,
                                    orgNm,
                                    processState
                                )
                            )

                            Log.d("js", "$resItems")

                        }
                    } else {
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

//    private fun intentSearch() {
//        header.icSearch.setOnClickListener {
//            val searchFragment = SearchFragment()
//            val transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_view, searchFragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}