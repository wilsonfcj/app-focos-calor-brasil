package com.ifsc.app.focos.queimadas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ifsc.app.focos.queimadas.ui.MapsActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity()
        }, 2000)

    }

    fun startActivity() {
        startActivity(Intent(this@SplashScreenActivity, MapsActivity::class.java))
        finish()
    }
}