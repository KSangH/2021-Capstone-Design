package com.basecamp.campong

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_CANCEL
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_RENTAL
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_RETURN
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_WAIT
import com.basecamp.campong.view.fragments.RentalStateFragment

class RentalPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RentalStateFragment(RENTAL_STATE_WAIT)
            1 -> RentalStateFragment(RENTAL_STATE_RENTAL)
            2 -> RentalStateFragment(RENTAL_STATE_RETURN)
            else -> RentalStateFragment(RENTAL_STATE_CANCEL)
        }
    }
}