package com.valentin.catsapi.dagger

import android.content.Context
import androidx.room.Room
import com.valentin.catsapi.activities.MainActivity
import com.valentin.catsapi.api.ApiHelper
import com.valentin.catsapi.api.CatApi
import com.valentin.catsapi.database.AppDatabase
import com.valentin.catsapi.fragments.CatsFragment
import com.valentin.catsapi.fragments.FavouriteFragment
import com.valentin.catsapi.repositories.CatsRepository
import com.valentin.catsapi.viewmodels.CatsViewModel
import com.valentin.catsapi.viewmodels.FavouriteViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CatsFragment)
    fun inject(fragment: FavouriteFragment)
}