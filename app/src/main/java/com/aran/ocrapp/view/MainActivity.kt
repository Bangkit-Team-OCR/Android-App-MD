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
import com.aran.ocrapp.databinding.ActivityMainBinding
import com.aran.ocrapp.helper.PostResponse
import com.aran.ocrapp.helper.Responses
import com.aran.ocrapp.helper.getAllDataResponse
import com.aran.ocrapp.model.HotelModel
import com.aran.ocrapp.viewmodel.HotelAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var hotelArrayList : ArrayList<HotelModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        binding.emailku.text = email

        binding.profile.setOnClickListener {
            val emaill = binding.emailku.text.toString()
            getAllEditedData(emaill)
        }

        val img = intArrayOf(

            R.drawable.h1, R.drawable.h3, R.drawable.h4, R.drawable.h5, R.drawable.h6,
            R.drawable.h7, R.drawable.h8, R.drawable.h9, R.drawable.h10, R.drawable.h2
        )

        val hotelName = arrayOf(
            "Bintan Lagoon Resort",
            "Pulau Nikoi",
            "Padma Hotel",
            "The Trans Luxury Hotel",
            "Royal Safari Garden Resort",
            "R Hotel, Rancamaya Bogor",
            "Aston Bogor Hotel and Resort",
            "Pesona Alam Resort & Spa",
            "The Ritz-Carlton",
            "Hard Rock Hotel Bali"
        )

        val location = arrayOf(
            "Jl. Indera Segara Site A12, Sebong Lagoi",
            "Pulau Nikoi, 8km lepas pantai timur Bintan",
            "Jl. Ranca Bentang 56-58 Ciumbuleuit, Bandung 40142",
            "Gatot Subroto 289, Batununggal, Jawa Barat, Indonesia 40273",
            "JL. Raya Puncak 601, Cisarua, Puncak, 16750",
            "Jl. Rancamaya Utama, Bogor 16720, Jawa Barat, Indonesia",
            "Bogor Nirwana Residence, Jl. Dreded Pahlawan",
            "Jalan Taman Safari No. 101, Kp. Baru Tegal",
            "Jalan Raya Nusa Dua Selatan No.Lot 3 Sawangan Nusa Dua",
            "Jalan Pantai, Banjar Pande Mas Kuta, Bali, Indonesia"
        )

        val price = arrayOf(
            "Rp 570.045",
            "Rp 458.030",
            "Rp 782.500",
            "Rp 524.672",
            "Rp 612.670",
            "Rp 852.123",
            "Rp 399.999",
            "Rp 673.782",
            "Rp 712.889",
            "Rp 581.324"
        )

        val room = arrayOf(
            "1", "4", "2", "3", "2",
            "2", "3", "1", "2", "1"
        )

        val bath = arrayOf(
            "2", "1", "2", "2", "1",
            "1", "1", "2", "3", "1"
        )

        val rating = arrayOf(
            "579", "346", "127", "1090", "756",
            "456", "123", "331", "901", "290"
        )

        hotelArrayList = ArrayList()

        for(i in hotelName.indices) {
            val hotel = HotelModel(img[i], hotelName[i], location[i], price[i], room[i], bath[i], rating[i])
            hotelArrayList.add(hotel)
        }

        binding.listView.isClickable = true
        binding.listView.adapter = HotelAdapter(this, hotelArrayList)
        binding.listView.setOnItemClickListener { parent, view, position, id ->

            val img = img[position]
            val hotelName = hotelName[position]
            val location = location[position]
            val price = price[position]
            val rating = rating[position]
            val room = room[position]
            val bath = bath[position]

            val i = Intent(this, DetailActivity::class.java)
            i.putExtra("img", img)
            i.putExtra("hotelName", hotelName)
            i.putExtra("location", location)
            i.putExtra("price", price)
            i.putExtra("rating", rating)
            i.putExtra("room", room)
            i.putExtra("bath", bath)
            startActivity(i)

        }
    }

    private fun getAllEditedData(email: String) {
        val client = ApiConfig.getApiService().getData(email)
        client.enqueue(object: Callback<getAllDataResponse> {
            override fun onResponse(
                call: Call<getAllDataResponse>,
                response: Response<getAllDataResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()

                Log.d(ControlsProviderService.TAG, "onResponse: $responseBody")

                if(response.isSuccessful && responseBody?.message == "getting user's profile successfully") {
                    Toast.makeText(this@MainActivity, getString(R.string.getting_success), Toast.LENGTH_SHORT).show()

                    val nik = responseBody.data.nik
                    val nama = responseBody.data.nama
                    val alamat = responseBody.data.alamat
                    val provinsi = responseBody.data.provinsi
                    val kabupaten = responseBody.data.kabupaten
                    val ttl = responseBody.data.ttl

                    val emailll = binding.emailku.text.toString()

                    val i = Intent(this@MainActivity, ProfileActivity::class.java)
                    i.putExtra("nik", nik)
                    i.putExtra("nama", nama)
                    i.putExtra("alamat", alamat)
                    i.putExtra("provinsi", provinsi)
                    i.putExtra("kabupaten", kabupaten)
                    i.putExtra("ttl", ttl)
                    i.putExtra("email", emailll)

                    startActivity(i)
                } else {
                    Log.e(ContentValues.TAG, "onFailure1: ${response.message()}")
                    Toast.makeText(this@MainActivity, getString(R.string.getting_fail), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<getAllDataResponse>, t: Throwable) {
                showLoading(false)
                Log.e(ControlsProviderService.TAG, "onFailure2: ${t.message}")
                Toast.makeText(this@MainActivity, getString(R.string.getting_fail), Toast.LENGTH_SHORT).show()
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