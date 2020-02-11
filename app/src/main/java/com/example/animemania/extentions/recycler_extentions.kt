package com.example.animemania.extentions


fun androidx.recyclerview.widget.RecyclerView.configureRecycler(isVertical: Boolean = true): androidx.recyclerview.widget.RecyclerView {
    itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context,
        if (isVertical) androidx.recyclerview.widget.RecyclerView.VERTICAL else androidx.recyclerview.widget.RecyclerView.HORIZONTAL, false)
    return this
}

