package com.valentin.catsapi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.valentin.catsapi.R
import com.valentin.catsapi.adapters.ViewPagerAdapter
import com.valentin.catsapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pagerAdapter = ViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }
}