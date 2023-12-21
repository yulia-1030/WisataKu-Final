package com.example.wisataku.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wisataku.R
import com.example.wisataku.data.model.Tour
import com.example.wisataku.data.model.TourData
import com.example.wisataku.databinding.ActivityMainBinding
import com.example.wisataku.ui.ViewModelFactory
import com.example.wisataku.ui.adapter.TourAdapter
import com.example.wisataku.ui.map.MapActivity
import com.example.wisataku.ui.profile.ProfileActivity
import com.example.wisataku.ui.welcome.WelcomeActivity
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    private lateinit var rvTour: RecyclerView
    private var list: ArrayList<Tour> = arrayListOf()

    private lateinit var tourAdapter: TourAdapter
    private lateinit var originalList: List<Tour>

    private val viewModel: MainViewModel by viewModels {

        ViewModelFactory.getInstance(this)

    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->

            if (!user.isLogin) {

                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()

            }

        }

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.mtTour))

        rvTour = findViewById(R.id.rvTour)
        rvTour.setHasFixedSize(true)

        list.addAll(TourData.listData)

        showRecyclerList()

        Toast.makeText(this, "WisataKu", Toast.LENGTH_SHORT).show()

        val tourSearchView = findViewById<SearchView>(R.id.svTour)

        tourSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tourAdapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.iProfile) {

            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)

        }


        if (item.itemId == R.id.iMap) {

            val intent = Intent(this@MainActivity, MapActivity::class.java)
            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)

    }

    private fun showRecyclerList (){
        rvTour.layoutManager = LinearLayoutManager(this)
        tourAdapter = TourAdapter(list)
        rvTour.adapter = tourAdapter
    }

}