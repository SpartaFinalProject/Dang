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
import com.android.dang.dictionary.data.BreedsSpinnerData
import com.android.dang.dictionary.data.breedTranslations
import com.android.dang.dictionary.data.temperamentTranslations
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
        binding.dictionaryRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        //프로그래스바 시작
        binding.progressDictionary.visibility = View.VISIBLE

        lifecycleScope.launch {
            //품종리스트 전체확보를 위해서 200으로 지정
            var breedsDatas = NetWorkClient.dogNetWork.getBreeds(
                NetWorkClient.API_AUTHKEY, hashMapOf(
                    "limit" to 200,
                    "page" to 0
                )
            )
            breedsDatas.apply {
                forEach {
                    it.nameKor = breedTranslations[it.name] ?: it.name
                    // temperament 값을 쉼표를 기준으로 분리하고 3개만 가져옴
                    val temperamentsList = (it.temperament?.split(", ") ?: listOf()).take(3)

                    // 각 키워드를 번역하고 다시 합침
                    it.temperamentKor = temperamentsList.joinToString(", ") { keyword ->
                        temperamentTranslations[keyword.trim()] ?: keyword
                    }

                    //성격이 없어서 직접입력
                    when (it.name) {
                        "Russian Toy" -> it.temperamentKor = "활기찬, 호기심많은, 다정한"
                        "Wirehaired Vizsla" -> it.temperamentKor = "순종적, 우호적, 훈련가능한"
                        "Poodle (Miniature)", "Poodle (Toy)" -> it.temperamentKor = "똑똑한, 애정이 많은, 활발한"
                    }
                }
                sortBy { it.nameKor }
            }

            mBreedList.clear()
            mBreedList.addAll(breedsDatas.map { breedItem ->
                BreedsSpinnerData(breedItem.id, breedItem.nameKor)
            })
            mBreedList.add(0, BreedsSpinnerData(0, "전체(${mBreedList.size})"))

            requireActivity().runOnUiThread {
                binding.dictionarySpinner.adapter =
                    BreedsSpinnerAdapter(requireContext(), mBreedList)
                binding.dictionarySpinner.onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position == 0) {
                            (binding.dictionaryRecyclerView.adapter as DictionaryListAdapter).addItems(
                                breedsDatas,
                                true
                            )
                            return
                        }

                        val id = mBreedList[position].id
                        breedsDatas.find { it.id == id }?.let {
                            (binding.dictionaryRecyclerView.adapter as DictionaryListAdapter).addItems(
                                arrayListOf(it),
                                true
                            )
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
                //프로그래스바가 끝
                binding.progressDictionary.visibility = View.GONE
            }
        }
    }

}