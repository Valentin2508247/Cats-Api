package com.valentin.catsapi.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.valentin.catsapi.R
import com.valentin.catsapi.adapters.CatFragmentListener
import com.valentin.catsapi.adapters.FavouriteFragmentListener
import com.valentin.catsapi.adapters.ViewPagerAdapter
import com.valentin.catsapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MainActivity : AppCompatActivity(), CatFragmentListener, FavouriteFragmentListener {
    private lateinit var binding: ActivityMainBinding
    private val pagerAdapter = ViewPagerAdapter(this)
    private lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = pagerAdapter
        binding.apply {
            TabLayoutMediator(tabLayout, viewPager) {tab, position ->
                when (position){
                    0 -> {
                        tab.text = "Cats"
                        tab.setIcon(R.drawable.outline_pets_black_48)
                    }
                    1 -> {
                        tab.text = "Favourite"
                        tab.setIcon(R.drawable.ic_baseline_favorite_border_24)
                    }
                }
            }.attach()
        }
        scope = CoroutineScope(Dispatchers.IO)
    }

    override fun onStop() {
        scope.cancel()
        super.onStop()
    }

    override fun downloadImage(url: String) {

    }
}