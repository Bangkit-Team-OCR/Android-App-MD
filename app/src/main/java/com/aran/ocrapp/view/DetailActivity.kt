package com.aran.ocrapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aran.ocrapp.R
import com.aran.ocrapp.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        var imgUri = "https://images.pexels.com/photos/96444/pexels-photo-96444.jpeg?cs=srgb&dl=pexels-francesco-ungaro-96444.jpg&fm=jpg"
//
//        Picasso.get().load(imgUri).fit().into(binding.detailPhoto)


        val img = intent.getIntExtra("img", R.drawable.hotel1)
        val hotelName = intent.getStringExtra("hotelName")
        val location = intent.getStringExtra("location")
        val price = intent.getStringExtra("price")
        val rating = intent.getStringExtra("rating")
        val room = intent.getStringExtra("room")
        val bath = intent.getStringExtra("bath")

        binding.detailPhoto.setImageResource(img)
        binding.hotelName.text = hotelName
        binding.location.text = location
        binding.price.text = price
        binding.rating.text = rating
        binding.room.text = room
        binding.bath.text = bath
    }
}