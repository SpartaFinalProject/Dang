package com.android.dang.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.dang.R
import com.android.dang.databinding.FragmentSearchBinding
import com.android.dang.search.adapter.RecentAdapter
import com.android.dang.search.adapter.SearchAdapter
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.search.searchViewModel.SearchViewModel
import com.android.dangtheland.retrofit.Constants
import com.android.dangtheland.retrofit.DangClient.api
import com.android.dangtheland.retrofit.abandonedDog.AbandonedDog
import com.android.dangtheland.retrofit.kind.Kind
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get()=_binding!!
    private lateinit var searchViewModel: SearchViewModel

    private var searchItem = mutableListOf<SearchDogData>()


    private var hashMap = HashMap<String, String>()

    private var recentList = mutableListOf<String>()

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var recentAdapter: RecentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        initView()
        viewModel()

        binding.searchEdit.setOnClickListener {
            binding.rcvSearchList.visibility = View.INVISIBLE
            binding.rcvRecentList.visibility = View.VISIBLE
        }

        binding.searchBtn.setOnClickListener {
            binding.rcvSearchList.visibility = View.VISIBLE
            binding.rcvRecentList.visibility = View.INVISIBLE
            searchViewModel.clearSearches()
            searchItem.clear()
            val dogKind = binding.searchEdit.text
            val kindNumber = hashMap[dogKind.toString()]
            searchData(kindNumber.toString())
        }

        var autoCompleteTextView = binding.searchEdit
        var adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, recentList)
        autoCompleteTextView.setAdapter(adapter)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        searchAdapter = SearchAdapter()
        binding.rcvSearchList.apply {
            adapter = searchAdapter
        }

        recentAdapter = RecentAdapter()
        binding.rcvRecentList.apply {
            adapter = recentAdapter
        }

        kindData()
    }

    private fun viewModel(){
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        searchViewModel.searchesList.observe(viewLifecycleOwner, Observer { list ->
            if (list != null) {
                searchAdapter.searchesData(list)
            }
        })
    }

    private fun searchData(kind: String) {
        api.abandonedDogSearch(
            Constants.AUTH_HEADER,
            417000,
            "json",
            10,
            kind
        ).enqueue(object : Callback<AbandonedDog?> {
            override fun onResponse(call: Call<AbandonedDog?>, response: Response<AbandonedDog?>) {
                if (response.isSuccessful) {
                    val abandonedDog = response.body()
                    abandonedDog?.response?.body?.items?.item?.forEach { item ->
                        val image = item.filename
                        val kindCd = item.kindCd
                        val age = item.age
                        val careAddr = item.careAddr
                        val processState = item.processState
                        val sexCd = item.sexCd
                        val neuterYn = item.neuterYn
                        val weight = item.weight
                        val specialMark = item.specialMark

                        searchItem.add(
                            SearchDogData(
                                image,
                                kindCd,
                                age,
                                careAddr,
                                processState,
                                sexCd,
                                neuterYn,
                                weight,
                                specialMark
                            )
                        )
                    }
                } else {
                    Log.e("api", "Error: ${response.errorBody()}")
                }
                searchViewModel.searches(searchItem)
            }

            override fun onFailure(call: Call<AbandonedDog?>, t: Throwable) {
                Log.e("#api1", "실패: ${t.message}")
            }

        })
    }
    private fun kindData() {
        api.kindSearch(
            Constants.AUTH_HEADER
        ).enqueue(object : Callback<Kind?> {
            override fun onResponse(call: Call<Kind?>, response: Response<Kind?>) {
                if (response.isSuccessful) {
                    val kind = response.body()
                    kind?.response?.body?.items?.item?.forEach { item ->
                        val kind = item.knm
                        val kindNumber = item.kindCd

                        hashMap[kind] = kindNumber
                        recentList.add(kind)
                    }
                    Log.d("aaaaaa", "$hashMap")
                } else {
                    Log.e("api", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Kind?>, t: Throwable) {
                Log.e("#api1", "실패: ${t.message}")
            }

        })
    }
}