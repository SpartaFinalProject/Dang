package com.android.dang.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dang.databinding.FragmentDictionaryBinding
import com.android.dang.dictionary.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding
    private var mBreedList = arrayListOf<BreedsSpinnerData>()
    private val dictionaryListAdapter by lazy {
        DictionaryListAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dictionaryRecyclerView.adapter = dictionaryListAdapter
        binding.dictionaryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        lifecycleScope.launch {
            var breedsDatas = NetWorkClient.dogNetWork.getBreeds(
                NetWorkClient.API_AUTHKEY, hashMapOf(
                    "limit" to 200,
                    "page" to 0
                )
            )

//            var breedItem = NetWorkClient.dogNetWork.getBreed(NetWorkClient.API_AUTHKEY, 3)
            requireActivity().runOnUiThread {
                mBreedList.clear()
                mBreedList.add(BreedsSpinnerData(0, "전체"))
                mBreedList.addAll(breedsDatas.map { breedItem ->
                    BreedsSpinnerData(breedItem.id, breedItem.name)
                })

                binding.dictionarySpinner.adapter = BreedsSpinnerAdapter(requireContext(), mBreedList)
                binding.dictionarySpinner.onItemSelectedListener = object : OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                        if(position == 0) {
                            (binding.dictionaryRecyclerView.adapter as DictionaryListAdapter).addItems(breedsDatas, true)
                            return
                        }

                        val id = mBreedList[position].id
                        breedsDatas.find {it.id == id}?.let {
                            (binding.dictionaryRecyclerView.adapter as DictionaryListAdapter).addItems(arrayListOf(it), true)
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
            }
        }
    }

}