package com.example.ktnotes

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    override fun onResume() {
        super.onResume()
        setupBottomBar()
    }

    /**
     * Activity's bottom bar is required to change in different fragment.
     */
    abstract fun setupBottomBar()
}