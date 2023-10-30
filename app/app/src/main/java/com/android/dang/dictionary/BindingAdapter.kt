package com.android.dang.dictionary

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.dang.dictionary.data.BreedsData
import com.bumptech.glide.Glide

@BindingAdapter("imgRes")
fun imgRes(imageView: ImageView, item: BreedsData.BreedsDataItem) {
    //이미지 id만 전달받아서 매번 api조회하면, 비효율. 이미지 경로를 지정하고 id만 부여하는 방식으로 적용
    val extension = if (item.reference_image_id in listOf("HkC31gcNm", "B12uzg9V7", "_Qf9nfRzL")) ".png" else ".jpg"
    val imgUrl = "https://cdn2.thedogapi.com/images/${item.reference_image_id}$extension"

    Glide.with(imageView.context)
        .load(imgUrl)
        .into(imageView)
}

@BindingAdapter("dicItemDes")
fun dicItemDes(textView: TextView, item: BreedsData.BreedsDataItem) {
    val weight = item.weight?.metric
    if (!weight.isNullOrEmpty()) {
        val weightParts = weight.split("-")
        if (weightParts.size == 2) {
            val minWeight = weightParts[0].trim()
            val maxWeight = weightParts[1].trim()
            textView.text =
                "평균체중 : $minWeight - $maxWeight kg\n평균수명 : ${item.life_span} \n성격 : ${item.temperament}"
        }
    }
}