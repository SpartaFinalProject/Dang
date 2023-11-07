package com.android.dang.shelter.shelterresult

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dang.MainViewModel
import com.android.dang.databinding.FragmentShelterResultBinding
import com.android.dang.retrofit.Constants
import com.android.dang.retrofit.abandonedDog.AbandonedDog
import com.android.dang.retrofit.abandonedDog.AbandonedShelter

class ShelterResultFragment : Fragment() {

    private lateinit var binding: FragmentShelterResultBinding
    private lateinit var mainViewModel: MainViewModel
    private var adapter: ShelterResultAdapter? = null
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
        }
    }

    private val onClickDog: (AbandonedShelter) -> Unit = { dog ->
        Log.d(Constants.TestTAG, "onClickDog: $dog")
    }
}