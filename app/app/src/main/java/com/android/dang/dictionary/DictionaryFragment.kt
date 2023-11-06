package com.android.dang.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.FragmentDictionaryBinding
import com.android.dang.dictionary.data.BreedsData
import com.android.dang.dictionary.data.BreedsSpinnerData
import com.android.dang.dictionary.data.breedTranslations
import com.android.dang.dictionary.data.temperamentTranslations
import com.android.dang.dictionary.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding
    private var mBreedList = arrayListOf<BreedsSpinnerData>()
    private var mBreedsViewData = arrayListOf<BreedsData.BreedsDataItem>()
    private var mPageCount = 0
    private lateinit var breedsDatas: BreedsData
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

        // 스크롤 리스너 추가 시작
        binding.dictionaryRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
           override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // 마지막 아이템이 화면에 보이면 다음 데이터 로드
                if (lastVisibleItem + 1 == totalItemCount && totalItemCount != 1) {
                    // 데이터 로드하기
                    loadMoreData()  // 여기서 loadMoreData는 다음 페이지의 데이터를 로드하는 함수입니다.
                }
            }

           override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
                val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
                var isTop = true

                if (!binding.dictionaryRecyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 스크롤이 맨 위에 있고 스크롤이 멈춘 경우
                    binding.floatBtn01.startAnimation(fadeOut)
                    binding.floatBtn01.visibility = View.GONE
                    isTop = true
                } else {
                    if (isTop) {
                        // 맨 위에 있지 않고 이전에 맨 위에 있었던 경우
                        binding.floatBtn01.visibility = View.VISIBLE
                        binding.floatBtn01.startAnimation(fadeIn)
                        isTop = false
                    }
                }
            }
        })
        binding.floatBtn01.setOnClickListener {
            // 플로팅 액션 버튼 클릭 시 RecyclerView를 맨 위로 부드럽게 스크롤
            binding.dictionaryRecyclerView.smoothScrollToPosition(0)
        }


        dictionaryListAdapter.itemClick = object : DictionaryListAdapter.ItemClick {
            override fun onClick(position: Int, item: BreedsData.BreedsDataItem) {
                item.nameKor?.let {
                    (activity as? OnDictionaryListener)?.onDictionaryItemSelected(it)
                }
            }
        }

        //프로그래스바 시작
        binding.progressDictionary.visibility = View.VISIBLE

        lifecycleScope.launch {
            //품종리스트 전체확보를 위해서 200으로 지정
            breedsDatas = NetWorkClient.dogNetWork.getBreeds(
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

            mPageCount = 1
            mBreedsViewData.clear()
            mBreedsViewData.addAll(breedsDatas.take(30 * mPageCount))  // 첫 30개의 데이터만 추가

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
                                mBreedsViewData,
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

    private fun loadMoreData() {
        mPageCount++
        mBreedsViewData.clear()
        mBreedsViewData.addAll(breedsDatas.take(30 * mPageCount))  // 첫 30개의 데이터만 추가
        (binding.dictionaryRecyclerView.adapter as DictionaryListAdapter).addItems(mBreedsViewData, true)
    }
}