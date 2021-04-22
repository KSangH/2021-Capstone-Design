package com.basecamp.campong

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RentalPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RentalState1Fragment()
            1 -> RentalState2Fragment()
            2 -> RentalState4Fragment()
            else -> RentalState0Fragment()
        }
    }
}