package com.example.confinout.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.adapter.NotifAdapter
import com.example.confinout.model.Notif

class NotifHolder(val view: View, notifAdapter: NotifAdapter) : RecyclerView.ViewHolder(view) {

    var date : TextView? = null
    var message : TextView? = null

    init {
        date = view.findViewById(R.id.notif_date)
        message = view.findViewById(R.id.notif_message)
    }

    fun bind(notif: Notif){
        date!!.text = notif.date
        message!!.text = notif.message
    }
}