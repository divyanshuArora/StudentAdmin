package com.divyanshu.studentadmin.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.divyanshu.studentadmin.R
import com.divyanshu.studentadmin.databinding.ActivitySplashBinding
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    var splashActivitySplashBinding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 23) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        splashActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        Handler().postDelayed({
            startActivity<DashboardActivity>()
            finish()
        }, 400)
    }
}

