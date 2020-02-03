package com.example.applaudochallange.di

import com.example.applaudochallange.ui.LobbyViewModel
import com.example.applaudochallange.ui.detailAnimeManga.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModules = module {
    viewModel{ LobbyViewModel(get())}
    viewModel { DetailViewModel(get()) }

}