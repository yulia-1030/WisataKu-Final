package com.example.wisataku.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisataku.R
import com.example.wisataku.databinding.ActivityProfileBinding
import com.example.wisataku.ui.ViewModelFactory
import com.example.wisataku.ui.main.MainViewModel
import com.example.wisataku.ui.welcome.WelcomeActivity
import com.google.android.material.appbar.MaterialToolbar

class ProfileActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.mtProfile)
        setSupportActionBar(toolbar)

        viewModel.getSession().observe(this) { user ->
            binding.tvEmail.text = user.email
        }

        Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.iLogout) {
            viewModel.logout()
            val intent = Intent(this@ProfileActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}