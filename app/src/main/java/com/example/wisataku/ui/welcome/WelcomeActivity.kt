package com.example.wisataku.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.wisataku.databinding.ActivityWelcomeBinding
import com.example.wisataku.ui.login.LoginActivity
import com.example.wisataku.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()

    }

    private fun setupView() {

        @Suppress("DEPRECATION")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            window.insetsController?.hide(WindowInsets.Type.statusBars())

        }

        else {

            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

        }

        supportActionBar?.hide()

    }

    private fun setupAction() {

        binding.loginBtn.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))

        }

        binding.registerBtn.setOnClickListener {

            startActivity(Intent(this, RegisterActivity::class.java))

        }

    }

    private fun playAnimation() {

        ObjectAnimator.ofFloat(
            binding.ivLogo,
            View.TRANSLATION_X,
            -30f, 30f
        ).apply {

            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE

        }.start()

        val appName = ObjectAnimator.ofFloat(
                binding.tvAppName,
                View.ALPHA,
                1f
            ).setDuration(100)
        val appMotto = ObjectAnimator.ofFloat(
                binding.tvAppMotto,
                View.ALPHA,
                1f
            ).setDuration(100)
        val loginButton = ObjectAnimator.ofFloat(
                binding.loginBtn,
                View.ALPHA,
                1f
            ).setDuration(100)
        val registerButton = ObjectAnimator.ofFloat(
                binding.registerBtn,
                View.ALPHA,
                1f
            ).setDuration(100)

        val together = AnimatorSet().apply {

            playTogether(loginButton, registerButton)

        }

        AnimatorSet().apply {

            playSequentially(
                appName,
                appMotto,
                together
            )
            start()

        }

    }

}