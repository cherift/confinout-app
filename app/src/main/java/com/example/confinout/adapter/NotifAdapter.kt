package com.example.confinout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.fragment.NotifFragment
import com.example.confinout.holder.NotifHolder
import com.example.confinout.model.Notif

class NotifAdapter (val fragment: NotifFragment) : RecyclerView.Adapter<NotifHolder>() {
    var notifications: MutableList<Notif> = mutableListOf<Notif>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notif_card_view, parent, false)

        return NotifHolder(view, this)
    }

    /**
     * Gets notifations list size
     */
    override fun getItemCount(): Int {
        return notifications.size
    }

    /**
     * Gives the notifications to view holder
     */
    override fun onBindViewHolder(holder: NotifHolder, position: Int) {
        holder.bind(notifications[position])
    }

    /**
     * Sets the list of notifications
     *
     * @param notifs : the list of notifications
     */
    fun bindViewModels(notifs: MutableList<Notif>) {
        notifications = notifs
        notifyDataSetChanged()
    }

}