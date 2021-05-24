package com.basecamp.campong

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_CANCEL
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_RENTAL
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_RETURN
import com.basecamp.campong.utils.RentalState.RENTAL_STATE_WAIT
import com.basecamp.campong.view.fragments.LendStateFragment

class LendPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LendStateFragment(RENTAL_STATE_WAIT)
            1 -> LendStateFragment(RENTAL_STATE_RENTAL)
            2 -> LendStateFragment(RENTAL_STATE_RETURN)
            else -> LendStateFragment(RENTAL_STATE_CANCEL)
        }
    }
}