package com.example.applaudochallange.data.external.kitsuApiService

import com.example.applaudochallange.models.AnimeManga
import java.io.Serializable

data class CharacterResponse(
    val data : AnimeManga,
    val status : String
): Serializable