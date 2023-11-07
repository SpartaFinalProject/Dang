package com.android.dang.shelter.shelterresult

import android.util.Log
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.R
import com.android.dang.databinding.ItemCommonDetailBinding
import com.android.dang.retrofit.Constants
import com.android.dang.search.searchItemModel.SearchDogData
import com.bumptech.glide.Glide

class DogItemViewHolder(
    private val binding: ItemCommonDetailBinding
) : RecyclerView.ViewHolder(binding.root) {
    private fun ellipsizeText(
        age: String?,
        careAddr: String?,
        processState: String?,
        sexCd: String?,
        neuterYn: String?,
        weight: String?,
        specialMark: String?,
        maxLength: Int
    ): String {
        val sexText = when (sexCd){
            "M" -> "수컷"
            "F" -> "암컷"
            else -> "미상"
        }

        val neuter = when (neuterYn){
            "Y" -> "중성화"
            "N" -> ""
            else -> "미상"
        }
        val ellipstext =
            "#${age ?: ""} ${careAddr ?: ""} #${processState ?: ""} #${sexText ?: ""} #${neuter ?: ""}#${weight ?: ""} \n#${specialMark ?: ""} "
        return ellipstext.ellipsize(maxLength)
    }

    private fun String.ellipsize(maxLength: Int): String {
        return if (length > maxLength) {
            "${substring(0, maxLength - 3)}..."
        } else {
            this
        }
    }

    fun setItem(dog: SearchDogData) {
        Log.d(Constants.TestTAG, "setItem: $dog")


        val address = dog.careAddr
        val parts = address?.split(" ")
        val result = "#${parts?.get(0)} ${parts?.get(1)}"
        Glide.with(binding.root)
            .load(
                dog.popfile
                    ?.toUri()
                    ?.buildUpon()
                    ?.scheme("https")
                    ?.build()
            ).into(binding.dogImg)
        val modifiedKindCd = dog.kindCd?.replace("[개]", "")?.trim() ?: ""
        binding.dogName.text = modifiedKindCd

        val processText = ellipsizeText(
            dog.age,
            result,
            dog.processState,
            dog.sexCd,
            dog.neuterYn,
            dog.weight,
            dog.specialMark,
            70
        )
        binding.dogTag.text = processText

        if (dog.isLiked!!) {
            binding.dogLike.setImageResource(R.drawable.icon_like_on)
        } else {
            binding.dogLike.setImageResource(R.drawable.icon_like_off)
        }
    }
}