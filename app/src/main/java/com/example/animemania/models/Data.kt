package com.example.animemania.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Data(
    val id : String?,
    val type : String?,
    var attributes : Attributes?
) :  Parcelable