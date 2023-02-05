package com.niuan.googlemaps

import android.app.Application
import com.niuan.googlemaps.core.utils.modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application(){

    private val myModule = listOf(modules)

    companion object{
        lateinit var instance : App
    }

    init{
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }


    }
}