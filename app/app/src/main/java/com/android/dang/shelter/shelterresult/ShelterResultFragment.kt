package com.android.dang.shelter.shelterresult

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.MainViewModel
import com.android.dang.databinding.FragmentShelterResultBinding
import com.android.dang.home.HomeFragment
import com.android.dang.retrofit.Constants
import com.android.dang.search.searchItemModel.SearchDogData

class ShelterResultFragment : Fragment() {

    private lateinit var binding: FragmentShelterResultBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mContext: Context
    private var resItems: ArrayList<SearchDogData> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private var adapter: ShelterResultAdapter? = null
    private lateinit var passData: DogData

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShelterResultBinding.inflate(layoutInflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val lm = LinearLayoutManager(requireContext())
        adapter = ShelterResultAdapter(onClickDog)
        with(binding.rcvShelterDogs) {
            layoutManager = lm
            adapter = this@ShelterResultFragment.adapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.abandonedDogsList.value?.let {
            adapter?.addAll(it)
            if (it.isNotEmpty()) {
                val firstDog = it[0]
                val shelterName = "${firstDog.careNm} 에서"
                binding.shelterName.text = shelterName
            }
        }
        }

    fun onClick(view: View, position: Int) {
        passData.passHome(resItems[position])
    }

    interface DogData {
        fun passHome(list: SearchDogData)
    }

    fun dogData(data: DogData) {
        passData = data
    }
    }


    private val onClickDog: (SearchDogData) -> Unit = { dog ->
        Log.d(Constants.TestTAG, "onClickDog: $dog")
    }