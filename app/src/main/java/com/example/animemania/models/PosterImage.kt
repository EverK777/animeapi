package com.example.animemania.models

import java.io.Serializable

data class PosterImage(
    val tiny : String?,
    val small : String?,
    val medium : String?,
    val large : String?,
    val original : String?
) : Serializable