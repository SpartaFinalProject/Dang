package com.android.dang.shelter.shelterresult

import android.util.Log
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.dang.databinding.ItemCommonDetailBinding
import com.android.dang.retrofit.Constants
import com.android.dang.retrofit.abandonedDog.AbandonedShelter
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DogItemViewHolder(
    private val binding: ItemCommonDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setItem(dog: AbandonedShelter) {
        Log.d(Constants.TestTAG, "setItem: $dog")
        Glide.with(binding.root)
            .load(
                dog.popfile
                    ?.toUri()
                    ?.buildUpon()
                    ?.scheme("https")
                    ?.build()
            ).into(binding.dogImg)

        binding.dogName.text = dog.kindCd
        binding.dogTag.text = dog.specialMark
    }
}