package com.valentin.catsapi.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.valentin.catsapi.fragments.CatsFragment
import com.valentin.catsapi.fragments.FavouriteFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CatsFragment()
            1 -> FavouriteFragment()
            else -> CatsFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
