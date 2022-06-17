package com.aran.ocrapp.view

import android.content.Intent
import android.icu.number.NumberRangeFormatter.with
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aran.ocrapp.R
import com.aran.ocrapp.databinding.ActivityMainBinding
import com.aran.ocrapp.model.HotelModel
import com.aran.ocrapp.viewmodel.HotelAdapter
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.with
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var hotelArrayList : ArrayList<HotelModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val img = intArrayOf(
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/rev-bintan.jpg",
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/2.jpg",
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/rev-padmabandung.jpg",
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/rev-transhotel.jpg",
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/5.jpg",
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/rev-rancamaya.jpeg",
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/7.jpg",
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/rev-pesonaalam.jpg",
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/rev-ritzcarlton.jpg",
//            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/15-hotel-terbaik-di-indonesia-untuk-staycation-bersama-keluarga/14.jpg"

            R.drawable.hotel1, R.drawable.hotel2, R.drawable.hotel3, R.drawable.hotel4, R.drawable.hotel5,
            R.drawable.hotel6, R.drawable.hotel7, R.drawable.hotel8, R.drawable.hotel9, R.drawable.hotel10
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
}