package com.example.applaudochallange.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class AnimeManga(
    val id : String?,
    val type : String?,
    val attributes : Attributes?
) :  Parcelable