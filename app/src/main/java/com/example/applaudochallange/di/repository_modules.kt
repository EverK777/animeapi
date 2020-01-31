package com.example.applaudochallange.di

import com.example.applaudochallange.data.external.KitsuRepository
import org.koin.dsl.module


val repositoryModule = module {
    single { KitsuRepository() }
}