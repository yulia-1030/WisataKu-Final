package com.example.wisataku.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.wisataku.data.model.UserModel
import com.example.wisataku.databinding.ActivityLoginBinding
import com.example.wisataku.data.MessageState
import com.example.wisataku.ui.customview.EmailEditText
import com.example.wisataku.ui.customview.PasswordEditText
import com.example.wisataku.ui.ViewModelFactory
import com.example.wisataku.ui.customview.LoginButton
import com.example.wisataku.ui.main.MainActivity
import com.example.wisataku.ui.main.MainViewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {

        ViewModelFactory.getInstance(this)

    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var loginButton: LoginButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        emailEditText = binding.etEmail
        passwordEditText = binding.etPassword
        loginButton = binding.loginBtn

        emailEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

                setLoginButton()

            }

            override fun afterTextChanged(
                s: Editable
            ) {

            }

        })

        passwordEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

                setLoginButton()

            }

            override fun afterTextChanged(
                s: Editable
            ) {

            }

        })

        setupView()
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

    private fun playAnimation() {

        ObjectAnimator.ofFloat(
            binding.ivLogo, View.TRANSLATION_X, -30f, 30f
        ).apply {

            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE

        }.start()

        val title =
            ObjectAnimator.ofFloat(
                binding.tvTitle, View.ALPHA, 1f
            ).setDuration(100)
        val desc =
            ObjectAnimator.ofFloat(
                binding.tvDesc, View.ALPHA, 1f
            ).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(
                binding.tvEmail, View.ALPHA, 1f
            ).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(
                binding.etLayoutEmail, View.ALPHA, 1f
            ).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(
                binding.tvPassword, View.ALPHA, 1f
            ).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(
                binding.etLayoutPassword, View.ALPHA, 1f
            ).setDuration(100)
        val loginButton =
            ObjectAnimator.ofFloat(
                binding.loginBtn, View.ALPHA, 1f
            ).setDuration(100)

        AnimatorSet().apply {

            playSequentially(
                title,
                desc,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                loginButton
            )

            startDelay = 100

        }.start()

    }

    private fun login() {

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.login(
            email,
            password
        ).observe(this) { result ->

            if (result != null) {

                when (result) {

                    is MessageState.Loading -> {

                        showLoading(true)

                    }

                    is MessageState.Success -> {

                        val token = result.data.toString()

                        viewModel.saveSession(
                            UserModel(
                                email,
                                password,
                                token
                            )
                        )
                        showToast("Login successfully")
                        showLoading(false)

                        AlertDialog.Builder(this).apply {

                            setTitle("Login successfully")
                            setPositiveButton("Next") { _, _ ->

                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                                startActivity(intent)
                                finish()

                            }

                            create()
                            show()

                        }

                    }

                    is MessageState.Error -> {

                        showToast(result.error)
                        showLoading(false)

                    }

                }

            }

        }

    }

    private fun setLoginButton() {

        val emailResult =
            emailEditText.text.toString().isNotEmpty() && emailEditText.error == null
        val passwordResult =
            passwordEditText.text.toString().isNotEmpty() && passwordEditText.error == null

        if (emailResult && passwordResult) {

            loginButton.isEnabled = true

            loginButton.setOnClickListener {

                login()

            }

        }

        else {

            loginButton.isEnabled = false

        }

    }

    private fun showLoading(isLoading: Boolean) {

        binding.pbLogin.visibility =
            if (isLoading) View.VISIBLE

            else View.GONE

    }

    private fun showToast(message: String) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()

    }

}