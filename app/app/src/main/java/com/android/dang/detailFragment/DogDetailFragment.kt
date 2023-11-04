package com.android.dang.detailFragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.dang.R
import com.android.dang.databinding.FragmentDogDetailBinding
import com.android.dang.search.searchItemModel.SearchDogData
import com.android.dang.util.PrefManager
import com.bumptech.glide.Glide

class DogDetailFragment : Fragment(R.layout.fragment_dog_detail) {

    private lateinit var detailData : SearchDogData
    private var _binding: FragmentDogDetailBinding? = null
    private val binding: FragmentDogDetailBinding
        get() = _binding!!

    private lateinit var mContext: Context

    private lateinit var likeList : List<SearchDogData>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        likeList = PrefManager.getLikeItem(mContext)
        Log.d("fragment", "${likeList.size}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDogDetailBinding.bind(view)

        val btnCall = binding.btnCall
        val callNumber = detailData.officeTel

        btnCall.setOnClickListener {
            val phoneNumber = callNumber
            Log.d("num", "callNumber = $phoneNumber")
            if(callNumber.isNotEmpty()) {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel: $phoneNumber")
                startActivity(dialIntent)
            }
        }
        initView()
        Log.d("fragment", "${likeList.size}")
        binding.btnLike.setOnClickListener {
            var index = 0
            for (list in likeList){
                if (detailData.popfile == list.popfile){
                    detailData.isLiked = false
                    context?.let { PrefManager.deleteItem(it, detailData.popfile) }
                    binding.btnLike.setImageResource(R.drawable.icon_heart_empty)
                    break
                }
                index++
            }
            if (index == likeList.size){
                detailData.isLiked = true
                context?.let { PrefManager.addItem(it, detailData) }
                binding.btnLike.setImageResource(R.drawable.icon_heart_filled)
            }
        }

    }

    private fun initView(){
        Glide.with(this)
            .load(detailData.popfile)
            .into(binding.dogImg)
        val text2 = detailData.kindCd
        val result2 = text2.replace("[개] ", "")
        binding.dogName.text = result2
        binding.dogId.text = detailData.noticeNo
        var text1 = "# 접수 일시 - ${detailData.age}\n"
        text1 += "# 발견 장소 - ${detailData.happenPlace}\n\n"
        text1 += when (detailData.sexCd) {
            "M" -> "# 성별 - 수컷\n"
            "F" -> "# 성별 - 암컷\n"
            else -> "# 성별 - 미상\n"
        }
        text1 += "# 나이 - ${detailData.age}\n"
        text1 += "# 색상 - ${detailData.colorCd}\n"
        text1 += "# 체중 - ${detailData.weight}\n\n"
        text1 += "# 특징 - ${detailData.specialMark}\n\n"

        text1 += "보호 센터명 - ${detailData.careNm}\n"
        text1 += "보호소 전화 번호 - ${detailData.officeTel}\n"
        binding.dogInfo.text = text1
        for (list in likeList){
            if (detailData.popfile == list.popfile){
                detailData.isLiked = true
                break
            }  else {
                detailData.isLiked = false
            }
        }
        if (detailData.isLiked) {
            binding.btnLike.setImageResource(R.drawable.icon_heart_filled)
        } else {
            binding.btnLike.setImageResource(R.drawable.icon_heart_empty)
        }
    }

    fun receiveData(data: SearchDogData){
        detailData = data
    }

}