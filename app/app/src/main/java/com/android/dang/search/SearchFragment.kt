package com.android.dang.search

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.dang.R
import com.android.dang.databinding.FragmentSearchBinding
import com.android.dang.retrofit.Constants
import com.android.dang.retrofit.DangClient.api
import com.android.dang.retrofit.kind.Kind
import com.android.dang.search.adapter.SearchAdapter
import com.android.dang.search.adapter.SearchAdapter.Companion.typeOne
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.search.searchViewModel.RecentViewModel
import com.android.dang.search.searchViewModel.SearchViewModel
import com.android.dangtheland.retrofit.abandonedDog.AbandonedDog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate


class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var recentViewModel: RecentViewModel

    private var searchItem = mutableListOf<SearchDogData>()

    private var hashMap = HashMap<String, String>()

    private var autoWordList = mutableListOf<String>()

    private lateinit var searchAdapter: SearchAdapter

    private val year = LocalDate.now().year
    private var dogKind = ""

    private lateinit var passData: DogData


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        var kindNumber = ""

        initView()
        viewModel()

        context?.let { recentViewModel.getListFromPreferences(it) }
            ?.let { recentViewModel.saveRecent(it) }


        binding.searchEdit.setOnClickListener {
            typeOne = 1
            binding.recent.visibility = View.VISIBLE
            binding.searchTag.visibility = View.INVISIBLE
        }

        binding.searchBtn.setOnClickListener {
            typeOne = 0
            binding.recent.visibility = View.INVISIBLE
            binding.searchTag.visibility = View.VISIBLE
            binding.textAge.text = "나이"
            binding.textGender.text = "성별"
            binding.textSize.text = "크기"

            dogKind = binding.searchEdit.text.toString()
            if (kindNumber != hashMap[dogKind] && dogKind.isNotEmpty()) {
                searchViewModel.clearSearches()
                searchItem.clear()
                kindNumber = hashMap[dogKind].toString()
                searchData(kindNumber)
            } else {
                toast("공백 이거나 검색이 완료된 검색어 입니다.")
            }
        }

        binding.searchAge.setOnClickListener {
            ageDialog()
        }

        binding.searchGender.setOnClickListener {
            genderDialog()
        }

        binding.searchSize.setOnClickListener {
            sizeDialog()
        }
        searchAdapter.itemClick = object : SearchAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                passData.pass(searchItem[position])
            }
            override fun onImageViewClick(position: Int) {
                recentViewModel.recentRemove(position)
            }

            override fun onTextViewClick(position: Int) {
                val edit = recentViewModel.editText(position)
                binding.searchEdit.setText(edit)
            }
        }

        val autoCompleteTextView = binding.searchEdit
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            autoWordList
        )
        autoCompleteTextView.setAdapter(adapter)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        searchAdapter = SearchAdapter()
        binding.rcvSearchList.apply {
            adapter = searchAdapter
        }

        kindData()
    }

    private fun viewModel() {
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        searchViewModel.searchesList.observe(viewLifecycleOwner, Observer { list ->
            if (list != null) {
                searchAdapter.searchesData(list)
            }
        })

        recentViewModel = ViewModelProvider(this)[RecentViewModel::class.java]

        recentViewModel.recentList.observe(viewLifecycleOwner, Observer { list ->
            if (list != null) {
                searchAdapter.recentData(list)
                context?.let { recentViewModel.saveListToPreferences(it) }
            }
        })
    }

    private fun recentAdd(text: String) {
        recentViewModel.recentAdd(text)
    }

    @SuppressLint("ResourceType")
    private fun ageDialog() {
        val builder = AlertDialog.Builder(requireContext())

        val v1 = layoutInflater.inflate(R.layout.dialog_search_age, null)
        builder.setView(v1)

        val dialog = builder.create()
        val applyBtn = v1.findViewById<TextView>(R.id.apply_btn)
        val resetBtn = v1.findViewById<TextView>(R.id.reset_btn)
        val oneYear = v1.findViewById<TextView>(R.id.one_year)
        val threeYear = v1.findViewById<TextView>(R.id.three_year)
        val fiveYear = v1.findViewById<TextView>(R.id.five_year)
        val sixYear = v1.findViewById<TextView>(R.id.six_year)
        val minAge = v1.findViewById<EditText>(R.id.set_min_age)
        val maxAge = v1.findViewById<EditText>(R.id.set_max_age)

        oneYear.setOnClickListener {
            minAge.setText("0")
            maxAge.setText("1")
        }

        threeYear.setOnClickListener {
            minAge.setText("1")
            maxAge.setText("3")
        }

        fiveYear.setOnClickListener {
            minAge.setText("3")
            maxAge.setText("5")
        }

        sixYear.setOnClickListener {
            minAge.setText("6")
            maxAge.text = null
        }

        applyBtn.setOnClickListener {


            val min = minAge?.text.toString().toInt()
            val max = maxAge?.text.toString().toInt()

            val minYear = year - min
            val maxYear = year - max
            searchViewModel.ageFilter(maxYear, minYear)
            binding.textAge.text = "$min ~ $max 살"
            dialog.dismiss()
        }

        resetBtn.setOnClickListener {
            searchViewModel.resetAgeFilter()
            binding.textAge.text = "나이"
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun genderDialog() {
        val builder = AlertDialog.Builder(requireContext())

        val v2 = layoutInflater.inflate(R.layout.dialog_search_gender, null)
        builder.setView(v2)

        val dialog = builder.create()
        val applyBtn = v2.findViewById<TextView>(R.id.apply_btn)
        val resetBtn = v2.findViewById<TextView>(R.id.reset_btn)
        var gender = "Q"
        var neutra = "N"
        var genderView = ""

        val male = v2.findViewById<ImageView>(R.id.set_male)
        val neutrality = v2.findViewById<ImageView>(R.id.set_neutrality)
        val female = v2.findViewById<ImageView>(R.id.set_female)

        male.setOnClickListener {
            gender = "M"
            genderView = "수컷"
            male.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_male,
                    null
                )
            )
            neutrality.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_neutrality_gray,
                    null
                )
            )
            female.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_female_gray,
                    null
                )
            )

        }
        neutrality.setOnClickListener {
            neutra = "Y"
            genderView = "중성"
            male.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_male_gray,
                    null
                )
            )
            neutrality.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_neutrality,
                    null
                )
            )
            female.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_female_gray,
                    null
                )
            )

        }
        female.setOnClickListener {
            gender = "F"
            genderView = "암컷"
            male.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_male_gray,
                    null
                )
            )
            neutrality.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_neutrality_gray,
                    null
                )
            )
            female.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.icon_female,
                    null
                )
            )
        }

        applyBtn.setOnClickListener {
            searchViewModel.genderFilter(gender)
            searchViewModel.neutrality(neutra)
            binding.textGender.text = "$genderView"
            dialog.dismiss()
        }

        resetBtn.setOnClickListener {
            searchViewModel.resetGenderFilter()
            searchViewModel.resetNeuterFilter()
            binding.textGender.text = "성별"
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun sizeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val v3 = layoutInflater.inflate(R.layout.dialog_search_size, null)
        builder.setView(v3)

        val dialog = builder.create()
        val applyBtn = v3.findViewById<TextView>(R.id.apply_btn)
        val resetBtn = v3.findViewById<TextView>(R.id.reset_btn)
        val minSize = v3.findViewById<EditText>(R.id.set_min_weight)
        val maxSize = v3.findViewById<EditText>(R.id.set_max_weight)
        val smallSize = v3.findViewById<TextView>(R.id.size_small)
        val mediumSize = v3.findViewById<TextView>(R.id.size_medium)
        val largeSize = v3.findViewById<TextView>(R.id.size_large)

        smallSize.setOnClickListener {
            minSize.setText("0")
            maxSize.setText("6")
        }
        mediumSize.setOnClickListener {
            minSize.setText("7")
            maxSize.setText("14")
        }
        largeSize.setOnClickListener {
            minSize.setText("15")
            maxSize.text = null
        }
        applyBtn.setOnClickListener {


            val min = minSize?.text.toString().toInt()
            val max = maxSize?.text.toString().toInt()

            searchViewModel.sizeFilter(min, max)
            binding.textSize.text = "$min ~ $max KG"
            dialog.dismiss()
        }

        resetBtn.setOnClickListener {
            searchViewModel.resetSizeFilter()
            binding.textSize.text = "크기"
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun searchData(kind: String) {
        api.abandonedDogSearch(
            Constants.AUTH_HEADER,
            417000,
            "json",
            30,
            kind
        ).enqueue(object : Callback<AbandonedDog?> {
            override fun onResponse(call: Call<AbandonedDog?>, response: Response<AbandonedDog?>) {
                if (response.isSuccessful) {
                    val abandonedDog = response.body()
                    abandonedDog?.response?.body?.items?.item?.forEach { item ->
                        val image = item.popfile
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
                                specialMark,
                                noticeNo,
                                happenPlace,
                                colorCd,
                                careNm,
                                careTel
                            )
                        )
                    }
                } else {
                    Log.e("api", "Error: ${response.errorBody()}")
                }
                searchViewModel.searches(searchItem)
                recentAdd(dogKind)
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
                        autoWordList.add(kind)
                    }
                } else {
                    Log.e("api", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Kind?>, t: Throwable) {
                Log.e("#api1", "실패: ${t.message}")
            }

        })
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    interface DogData {
        fun pass(list: SearchDogData)
    }

    fun dogData(data: DogData) {
        passData = data
    }
}