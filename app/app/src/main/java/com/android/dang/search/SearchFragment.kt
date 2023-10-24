package com.android.dang.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.dang.R
import com.android.dang.databinding.FragmentSearchBinding
import com.android.dang.search.searchAdapter.SearchAdapter
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.search.searchViewModel.SearchViewModel
import com.android.dang.retrofit.Constants
import com.android.dang.retrofit.DangClient.api
import com.android.dang.retrofit.abandonedDog.AbandonedDog
import com.android.dang.retrofit.abandonedDog.AbandonedDogRes
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

    private lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        initView()
        viewModel()
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
        searchData()
    }

    private fun viewModel(){
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        searchViewModel.searchesList.observe(viewLifecycleOwner, Observer { list ->
            if (list != null) {
                searchAdapter.searchesData(list)
            }
        })
    }

    private fun searchData() {
        api.abandonedDogSearch(
            Constants.AUTH_HEADER,
            417000,
            "json",
            10,
            "000054"
        ).enqueue(object : Callback<AbandonedDogRes?> {
            override fun onResponse(call: Call<AbandonedDogRes?>, response: Response<AbandonedDogRes?>) {
                if (response.isSuccessful) {
                    val abandonedDog = response.body()

                    abandonedDog?.response?.body?.items?.item?.forEach { item ->
                        val image = item?.filename ?: ""
                        val kindCd = item?.kindCd ?: ""
                        val age = item?.age ?: ""
                        val careAddr = item?.careAddr ?: ""
                        val processState = item?.processState ?: ""
                        val sexCd = item?.sexCd ?: ""
                        val neuterYn = item?.neuterYn ?: ""
                        val weight = item?.weight ?: ""
                        val specialMark = item?.specialMark ?: ""

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

            override fun onFailure(call: Call<AbandonedDogRes?>, t: Throwable) {
                Log.e("#api1", "실패: ${t.message}")
            }

        })
    }
}