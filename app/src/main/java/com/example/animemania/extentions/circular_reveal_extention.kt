package com.example.animemania.extentions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import kotlin.math.hypot

fun View.revealAnimation(bottomContainer : View){
    val cx = (this.width )
    val cy = (this.height)
    val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
    val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius)
    anim.start()
    this.visibility = View.VISIBLE
}

fun View.revealClose(){
    val cx = (this.width )
    val cy = (this.height)

    val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

    val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy,  finalRadius,0f)
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            this@revealClose.visibility = View.INVISIBLE
        }
    })
    anim.start()
}