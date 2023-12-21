package com.example.wisataku.ui.detailtour

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.wisataku.R
import com.example.wisataku.databinding.ActivityDetailTourBinding
import com.example.wisataku.databinding.ActivityMapBinding
import com.example.wisataku.ui.map.MapActivity
import com.example.wisataku.ui.profile.ProfileActivity

class DetailTourActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTourBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ivPhoto : ImageView = findViewById(R.id.ivPhoto)
        val tvName : TextView = findViewById(R.id.tvTitle)
        val tvAddress : TextView = findViewById(R.id.tvLocation)
        val tvDescription : TextView = findViewById(R.id.tvDesc)
        val tvRating : TextView = findViewById(R.id.tvRating)

        val photoAccepted = intent.getIntExtra("TOUR_PHOTO", 0)
        val nameAccepted = intent.getStringExtra("TOUR_NAME")
        val addressAccepted = intent.getStringExtra("TOUR_ADDRESS")
        val descriptionAccepted = intent.getStringExtra("TOUR_DESCRIPTION")
        val ratingAccepted = intent.getStringExtra("TOUR_RATING")

        ivPhoto.setImageResource(photoAccepted)
        tvName.text = nameAccepted
        tvAddress.text = addressAccepted
        tvDescription.text = descriptionAccepted
        tvRating.text = ratingAccepted

        Toast.makeText(this, "Detail", Toast.LENGTH_SHORT).show()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivLocation.setOnClickListener {
            val intent = Intent(this@DetailTourActivity, MapActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}