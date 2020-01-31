package com.example.applaudochallange.data.external.kitsuApiService

import com.example.applaudochallange.models.AnimeManga
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseResponse(
    @SerializedName("data")
    val animeMangaList : List<AnimeManga>

): Serializable