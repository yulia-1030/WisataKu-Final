package com.example.wisataku.ui.register

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
import com.example.wisataku.databinding.ActivityRegisterBinding
import com.example.wisataku.data.MessageState
import com.example.wisataku.ui.customview.EmailEditText
import com.example.wisataku.ui.customview.PasswordEditText
import com.example.wisataku.ui.ViewModelFactory
import com.example.wisataku.ui.customview.RegisterButton
import com.example.wisataku.ui.login.LoginActivity
import com.example.wisataku.ui.main.MainViewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {

        ViewModelFactory.getInstance(this)

    }

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var registerButton: RegisterButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        emailEditText = binding.etEmail
        passwordEditText = binding.etPassword
        registerButton = binding.registerBtn

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

                setRegisterButton()

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

                setRegisterButton()

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
        val tvDesc =
            ObjectAnimator.ofFloat(
                binding.tvDesc, View.ALPHA, 1f
            ).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(
                binding.tvName, View.ALPHA, 1f
            ).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(
                binding.etLayoutName, View.ALPHA, 1f
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
        val registerButton =
            ObjectAnimator.ofFloat(
                binding.registerBtn, View.ALPHA, 1f
            ).setDuration(100)

        AnimatorSet().apply {

            playSequentially(
                title,
                tvDesc,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                registerButton
            )

            startDelay = 100

        }.start()

    }

    private fun register() {

        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.register(
            name,
            email,
            password
        ).observe(this) { result ->

            if (result != null) {

                when (result) {

                    is MessageState.Loading -> {

                        showLoading(true)

                    }

                    is MessageState.Success -> {

                        showToast(result.data.toString())
                        showLoading(false)

                        AlertDialog.Builder(this).apply {

                            setTitle("Register Successfully!")

                            setPositiveButton("Login") { _, _ ->

                                val intent = Intent(context, LoginActivity::class.java)
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

    private fun setRegisterButton() {

        val emailResult =
            emailEditText.text.toString().isNotEmpty() && emailEditText.error == null
        val passwordResult =
            passwordEditText.text.toString().isNotEmpty() && passwordEditText.error == null

        if (emailResult && passwordResult) {

            registerButton.isEnabled = true

            registerButton.setOnClickListener {

                register()

            }

        }

        else {

            registerButton.isEnabled = false

        }

    }

    private fun showLoading(isLoading: Boolean) {

        binding.pbRegister.visibility =
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