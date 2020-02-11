package com.example.animemania.di

import com.example.animemania.ui.LobbyViewModel
import com.example.animemania.ui.detailAnimeManga.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModules = module {
    viewModel{ LobbyViewModel(get())}
    viewModel { DetailViewModel(get()) }

}