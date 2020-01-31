package com.example.applaudochallange

import android.app.Application
import com.example.applaudochallange.di.repositoryModule
import com.example.applaudochallange.di.viewModelModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val modules : ArrayList<Module> = ArrayList()
        modules.add(repositoryModule)
        modules.add(viewModelModules)

        startKoin {
            modules(modules)
        }
    }
}