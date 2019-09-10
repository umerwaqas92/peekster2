package com.peekster.Utils

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

open class MyActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemUI(getWindow());   //this hides NavigationBar before showing the activity
        super.onCreate(savedInstanceState)
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI(window)
    }

    fun hideSystemUI(window: Window) {

        window.getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        )

    }

}