package com.aran.ocrapp.view

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aran.ocrapp.R
import com.aran.ocrapp.api.ApiConfig
import com.aran.ocrapp.databinding.ActivityDetailDataBinding
import com.aran.ocrapp.helper.PostResponse
import com.aran.ocrapp.helper.Responses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailDataActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nik = intent.getStringExtra("nik")
        val nama = intent.getStringExtra("nama")
        val alamat = intent.getStringExtra("alamat")
        val provinsi = intent.getStringExtra("provinsi")
        val kabupaten = intent.getStringExtra("kabupaten")
        val agama = intent.getStringExtra("agama")
        val jk = intent.getStringExtra("jk")
        val kecamatan = intent.getStringExtra("kecamatan")
        val kelurahan = intent.getStringExtra("kelurahan")
        val pekerjaan = intent.getStringExtra("pekerjaan")
        val rt = intent.getStringExtra("rt")
        val ttl = intent.getStringExtra("ttl")
        val kewarganegaraan = intent.getStringExtra("kewarganegaraan")

        binding.nik.setText(nik)
        binding.nama.setText(nama)
        binding.alamat.setText(alamat)
        binding.provinsi.setText(provinsi)
        binding.kabupaten.setText(kabupaten)
        binding.agama.setText(agama)
        binding.jk.setText(jk)
        binding.kecamatan.setText(kecamatan)
        binding.desa.setText(kelurahan)
        binding.pekerjaan.setText(pekerjaan)
        binding.rt.setText(rt)
        binding.ttl.setText(ttl)
        binding.kewarganegaraan.setText(kewarganegaraan)

        binding.btnConfirm.setOnClickListener {

            val nikk = binding.nik.text.toString()
            val namaa = binding.nama.text.toString()
            val alamatt = binding.alamat.text.toString()
            val provinsii = binding.provinsi.text.toString()
            val kabupatenn = binding.kabupaten.text.toString()

            uploadPhotoToServer(nikk, namaa, alamatt, provinsii, kabupatenn)
        }
    }

    private fun uploadPhotoToServer(nik: String, nama: String, alamat: String, provinsi: String, kabupaten: String) {

        showLoading(true)
        Toast.makeText(this@DetailDataActivity, "Getting Your Data", Toast.LENGTH_SHORT).show()

        val client = ApiConfig.getApiService().postAllData(nik, nama, alamat, provinsi, kabupaten)
        client.enqueue(object: Callback<Responses> {
            override fun onResponse(
                call: Call<Responses>,
                response: Response<Responses>
            ) {
                showLoading(false)
                val responseBody = response.body()

                Log.d(ControlsProviderService.TAG, "onResponse: $responseBody")

                if(response.isSuccessful && responseBody?.message == "insert new profile success") {
                    Toast.makeText(this@DetailDataActivity, getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@DetailDataActivity, SignInActivity::class.java))
                } else {
                    Log.e(ContentValues.TAG, "onFailure1: ${response.message()}")
                    Toast.makeText(this@DetailDataActivity, getString(R.string.upload_failed), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Responses>, t: Throwable) {
                showLoading(false)
                Log.e(ControlsProviderService.TAG, "onFailure2: ${t.message}")
                Toast.makeText(this@DetailDataActivity, getString(R.string.getting_fail), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}