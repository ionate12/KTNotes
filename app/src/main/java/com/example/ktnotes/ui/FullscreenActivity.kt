package com.example.ktnotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.ktnotes.R
import com.example.ktnotes.databinding.ActivityFullscreenBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityFullscreenBinding
    var fabOnClickListener: ((View) -> Unit)? = null //onClickListener callback function to be called on different fragments.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fullscreen)
        binding.lifecycleOwner = this
        binding.fab.setOnClickListener {v ->
            fabOnClickListener?.let { it(v) }
        }
    }



}