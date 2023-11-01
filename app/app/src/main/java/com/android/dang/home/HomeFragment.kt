package com.android.dang.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.shelter.view.ShelterFragment
import com.android.dang.util.PrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private var resItems: ArrayList<SearchDogData> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        Log.d("homefragment", "onCreate")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        Log.d("homefragment", "onAttach")
    }

    override fun onResume() {
        super.onResume()
        Log.d("homefragment", "onResume")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.d("homefragment", "onCreateView")

        binding.bannerMoreBtn.setOnClickListener {
            val shelterFragment = ShelterFragment()
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
        Log.d("homeFragment", "homeResult")
        apiService.homeDang(
            Util.KEY, 50, "json", 417000
        )
            .enqueue(object : Callback<HomeData?> {
                override fun onResponse(call: Call<HomeData?>, response: Response<HomeData?>) {
                    if (response.isSuccessful) {
                        val homeData = response.body()
                        val likeItems = PrefManager.getLikeItem(mContext)
                        Log.d("homefragment","likeItems.size:${likeItems.size}")
                        homeData?.response?.body?.items?.item?.forEach { item ->
                            val popfile = item.popfile
                            val kindCd = item.kindCd
                            val age = item.age
                            val careAddr = item.careAddr
                            val processState = item.processState
                            val sexCd = item.sexCd
                            val neuterYn = item.neuterYn
                            val weight = item.weight
                            val specialMark = item.specialMark
                            val noticeNo = item.noticeNo
                            val happenPlace = item.happenPlace
                            val colorCd = item.colorCd
                            val careNm = item.careNm
                            val careTel = item.careTel
                            var isLike = false

                            val likedDog = likeItems.find { it.popfile == item.popfile }
                            if (likedDog != null) {
                               isLike = true
                            }
                            resItems.add(
                                SearchDogData(
                                    popfile,
                                    kindCd,
                                    age,
                                    careAddr,
                                    processState,
                                    sexCd,
                                    neuterYn,
                                    weight,
                                    specialMark,
                                    noticeNo,
                                    happenPlace,
                                    colorCd,
                                    careNm,
                                    careTel,
                                    isLike
                                )
                            )
                            Log.d("homefragment", "popfile = $popfile / isLike = $isLike")
                        }
                        Log.d("js", "$resItems")
                    } else {
                        Log.e("error", "${response.code()}")
                    }
                    adapter.addItem(resItems)

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