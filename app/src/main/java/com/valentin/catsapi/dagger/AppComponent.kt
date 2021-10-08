package com.valentin.catsapi.dagger

import android.content.Context
import com.valentin.catsapi.activities.MainActivity
import com.valentin.catsapi.fragments.CatsFragment
import com.valentin.catsapi.fragments.DetailFragment
import com.valentin.catsapi.fragments.FavouriteFragment
import dagger.BindsInstance
import dagger.Component

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
    fun inject(fragment: DetailFragment)
}
