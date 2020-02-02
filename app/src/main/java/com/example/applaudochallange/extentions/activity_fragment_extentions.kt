package com.example.applaudochallange.extentions

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.hideKeyboard (){
    val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) {
        view =  View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

