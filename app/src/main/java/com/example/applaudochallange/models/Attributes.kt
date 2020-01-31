package com.example.applaudochallange.models

import java.io.Serializable

data class Attributes(
    val canonicalTitle : String,
    val averageRating : String?,
    val startDate : String?,
    val endDate : String?,
    val posterImage :PosterImage
) : Serializable
