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
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.util.PrefManager

class ShelterResultFragment : Fragment(),ShelterResultAdapter.ItemClick {

    private lateinit var binding: FragmentShelterResultBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mContext: Context
    private var resItems: ArrayList<SearchDogData> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private var adapter: ShelterResultAdapter? = null
    private lateinit var passData: DogData
    private lateinit var likeList: List<SearchDogData>

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
        adapter = ShelterResultAdapter(mContext)
        with(binding.rcvShelterDogs) {
            layoutManager = lm
            adapter = this@ShelterResultFragment.adapter
        }
        recyclerView = binding.rcvShelterDogs
        recyclerView.layoutManager = LinearLayoutManager(context)



        resItems.clear()
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
        adapter?.itemClick = this
    }

    interface DogData {
        fun passShelter(list: SearchDogData)
    }

    fun dogData(data: DogData) {
        passData = data
    }
    override fun onClick(view: View, position: Int) {
        passData.passShelter(resItems[position])
        Log.d("shelter1", "${resItems[position].isLiked}")
    }

    override fun onLikeViewClick(position: Int) {
        likeList = PrefManager.getLikeItem(mContext)
        val saveDog = mainViewModel.shelterlikeList(position)
        var index = 0

        for (list in likeList) {
            if (saveDog.popfile == list.popfile) {
                saveDog.isLiked = false
                context?.let { saveDog.popfile?.let { it1 -> PrefManager.deleteItem(it, it1) } }
                adapter?.searchNew()
                break
            }
            index++
        }
        if (index == likeList.size) {
            saveDog.isLiked = true
            context?.let { PrefManager.addItem(it, saveDog) }
            adapter?.searchNew()
        }
    }

    }