package com.example.ktnotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import com.example.ktnotes.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_bottom_menu.view.*

class BottomMenuFragment: BottomSheetDialogFragment(), NavigationView.OnNavigationItemSelectedListener {
    var onItemSelected: ((CharSequence) -> Unit)? = null
    companion object {
        private const val KEY ="BottomMenu"
        fun newInstance(@MenuRes menuId: Int): BottomMenuFragment {
            return BottomMenuFragment().apply {
                this.arguments = Bundle().apply { putInt(KEY, menuId) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resId = arguments?.getInt(KEY) ?: 0
        view.navigation_view.apply {
            inflateMenu(resId)
            setNavigationItemSelectedListener(this@BottomMenuFragment)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        onItemSelected?.let { it(item.title) }
        dismiss()
        return true
    }
}

