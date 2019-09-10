package com.peekster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.peekster.Utils.MyActivity

class Splash_Screen : MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash__screen)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity_2::class.java))
            finish()
        },1000)
    }
}
