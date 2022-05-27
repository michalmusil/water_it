package cz.mendelu.xmusil5.waterit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import cz.mendelu.xmusil5.waterit.R
import cz.mendelu.xmusil5.waterit.databinding.ActivityMainBinding
import cz.mendelu.xmusil5.waterit.databinding.ActivitySplashSceenBinding

class SplashSceenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashSceenBinding
    private lateinit var mHandler: Handler

    companion object{
        val SPLASH_SCREEN_TIMEOUT: Long = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashSceenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mHandler = Handler()
        mHandler.postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_TIMEOUT)

    }
}