package com.valentin.catsapi.dagger

import android.content.Context
import androidx.room.Room
import com.valentin.catsapi.api.ApiHelper
import com.valentin.catsapi.api.CatApi
import com.valentin.catsapi.database.AppDatabase
import com.valentin.catsapi.repositories.CatsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object AppModule {

    @Provides
    fun provideCatsRepository(apiHelper: ApiHelper, database: AppDatabase): CatsRepository {
        return CatsRepository(
            apiHelper,
            database
        )
    }

    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "my_database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCatApi(): CatApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CatApi::class.java)
    }

    @Provides
    fun provideApiHelper(catApi: CatApi): ApiHelper {
        return ApiHelper(catApi)
    }
}