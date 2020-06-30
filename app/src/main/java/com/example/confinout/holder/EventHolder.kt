package com.example.confinout.holder

import android.view.View
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.adapter.EventAdapter
import com.example.confinout.model.MyEvent
import kotlinx.android.synthetic.main.event_card_view.view.*

class EventHolder(val view: View, eventAdapter: EventAdapter) : RecyclerView.ViewHolder(view) {

    var adder : ImageView? =null
    var place : TextView? = null
    var description : TextView? = null
    var price : TextView? = null
    var date : TextView? = null

    init {
        adder = view.findViewById(R.id.adder)
        place = view.findViewById(R.id.place)
        description = view.findViewById(R.id.description)
        price = view.findViewById(R.id.price)
        date = view.findViewById(R.id.date)

        adder!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val position: Int = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    eventAdapter.add(position)
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