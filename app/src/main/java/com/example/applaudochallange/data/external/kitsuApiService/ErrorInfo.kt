package com.example.applaudochallange.data.external.kitsuApiService

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorInfo (
   val status : String,
   val code : String
):Serializable