package com.example.confinout.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.adapter.FavoriteAdapter
import com.example.confinout.model.MyEvent

class FavoriteHolder(val view: View, favoriteAdapter: FavoriteAdapter) : RecyclerView.ViewHolder(view) {

    var remover : ImageView? =null
    var place : TextView? = null
    var description : TextView? = null
    var price : TextView? = null
    var date : TextView? = null

    init {
        remover = view.findViewById(R.id.remover)
        place = view.findViewById(R.id.fav_place)
        description = view.findViewById(R.id.fav_description)
        price = view.findViewById(R.id.fav_price)
        date = view.findViewById(R.id.fav_date)

        remover!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val position: Int = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    favoriteAdapter.remove(position)
                }
            }
        })
    }

    fun bind(myEvent: MyEvent){
        place!!.text = myEvent.place
        description!!.text = if (myEvent.description.trim() == "") "No description" else myEvent.description
        price!!.text = myEvent.price.toString()
        date!!.text = myEvent.date
    }
}