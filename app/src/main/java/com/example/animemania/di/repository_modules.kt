package com.example.animemania.di

import com.example.animemania.data.external.KitsuRepository
import org.koin.dsl.module


val repositoryModule = module {
    single { KitsuRepository() }
}