package com.example.confinout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.fragment.FavoriteFragment
import com.example.confinout.holder.FavoriteHolder
import com.example.confinout.model.MyEvent

class FavoriteAdapter(val fragment: FavoriteFragment) : RecyclerView.Adapter<FavoriteHolder>() {
    var events: MutableList<MyEvent> = mutableListOf<MyEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_card_view, parent, false)

        return FavoriteHolder(view, this)
    }

    /**
     * Gets event size
     */
    override fun getItemCount(): Int {
        return events.size
    }

    /**
     * Gives the events to view holder
     */
    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(events[position])
    }

    /**
     * Sets the list of events
     *
     * @param events : the list of events
     */
    fun bindViewModels(events: MutableList<MyEvent>){
        this.events = events
        notifyDataSetChanged()
    }

    fun remove(position: Int){
        fragment.remove(events[position])
    }

}