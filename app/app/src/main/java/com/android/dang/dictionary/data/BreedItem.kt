package com.android.dang.dictionary.data

data class BreedItem(
    var bred_for: String?,
    var breed_group: String?,
    var height: Height?,
    var id: Int?,
    var life_span: String?,
    var name: String?,
    var reference_image_id: String?,
    var temperament: String?,
    var weight: Weight?
) {
    data class Height(
        var imperial: String?,
        var metric: String?
    )

    data class Weight(
        var imperial: String?,
        var metric: String?
    )
}