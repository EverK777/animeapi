package com.example.applaudochallange.models

import java.io.Serializable

data class Attributes(
    val canonicalTitle : String,
    val averageRating : String?,
    val startDate : String?,
    val ageRatingGuide : String?,
    val endDate : String?,
    val episodeCount : String?,
    val posterImage :PosterImage,
    val coverImage : PosterImage?,
    val status : String?,
    val synopsis : String?

) : Serializable
