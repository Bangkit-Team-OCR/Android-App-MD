package com.aran.ocrapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aran.ocrapp.R
import com.aran.ocrapp.databinding.ActivityDetailDataBinding
import com.aran.ocrapp.databinding.ActivityMainBinding
import com.aran.ocrapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nik = intent.getStringExtra("nik")
        val nama = intent.getStringExtra("nama")
        val alamat = intent.getStringExtra("alamat")
        val provinsi = intent.getStringExtra("provinsi")
        val kabupaten = intent.getStringExtra("kabupaten")
        val ttl = intent.getStringExtra("ttl")
        val email = intent.getStringExtra("email")

        binding.nik.text = nik
        binding.nama.text = nama
        binding.alamat.text = alamat
        binding.provinsi.text = provinsi
        binding.kabupaten.text = kabupaten
        binding.ttl.text = ttl
        binding.email.text = email
    }
}