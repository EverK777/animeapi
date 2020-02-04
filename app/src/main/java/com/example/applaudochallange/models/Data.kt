package com.example.applaudochallange.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Data(
    val id : String?,
    val type : String?,
    var attributes : Attributes?
) :  Parcelable