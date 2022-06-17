package com.aran.ocrapp.viewmodel

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.aran.ocrapp.R
import com.aran.ocrapp.model.HotelModel
import de.hdodenhof.circleimageview.CircleImageView

class HotelAdapter (private val context : Activity, private val arrayList: ArrayList<HotelModel>) : ArrayAdapter<HotelModel>(context, R.layout.hotel_item, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.hotel_item, null)

//        val img  : CircleImageView = view.findViewById(R.id.img)
        val hotelName : TextView = view.findViewById(R.id.hotel_name)
        val location : TextView = view.findViewById(R.id.location)
        val price : TextView = view.findViewById(R.id.price)
        val rating : TextView = view.findViewById(R.id.rating)

//        img.setImageResource(arrayList[position].img)
        hotelName.text = arrayList[position].hotelName
        location.text = arrayList[position].location
        price.text = arrayList[position].price
        rating.text = arrayList[position].rating

        return view
    }
}