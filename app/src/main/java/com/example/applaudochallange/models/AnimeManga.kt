package com.example.applaudochallange.models

import java.io.Serializable

data class AnimeManga(
    val id : String,
    val type : String,
    val attributes : Attributes
) : Serializable