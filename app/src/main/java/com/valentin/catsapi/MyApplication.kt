package com.valentin.catsapi

import android.app.Application
import android.content.Context
import com.valentin.catsapi.dagger.AppComponent
import com.valentin.catsapi.dagger.DaggerAppComponent

class MyApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MyApplication -> appComponent
        else -> this.applicationContext.appComponent
    }
