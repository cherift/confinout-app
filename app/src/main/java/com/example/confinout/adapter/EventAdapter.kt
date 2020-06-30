package com.example.confinout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.fragment.EventsFragment
import com.example.confinout.holder.EventHolder
import com.example.confinout.model.MyEvent

class EventAdapter(val fragment: EventsFragment) : RecyclerView.Adapter<EventHolder>(), Filterable{

    var events: MutableList<MyEvent> = mutableListOf<MyEvent>()
    var eventsCopy: MutableList<MyEvent> = mutableListOf<MyEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_card_view, parent, false)

        return EventHolder(view, this)
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
    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(events[position])
    }

    /**
     * Sets the list of events
     *
     * @param events : the list of events
     */
    fun bindViewModels(events: MutableList<MyEvent>){
        this.events = events
        this.eventsCopy = events
        notifyDataSetChanged()
    }

    fun add(position: Int){
        fragment.addEvent(events[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterString: String = constraint.toString().toLowerCase()
                var results : FilterResults = FilterResults()
                var originalList: MutableList<MyEvent> =  eventsCopy
                var filteredList: MutableList<MyEvent> = mutableListOf()

                originalList.forEach { event ->
                    if (event.place.toLowerCase().contains(filterString)){
                        filteredList.add(event)
                    }
                }

                results.values = filteredList
                results.count = filteredList.size

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                events = results!!.values as MutableList<MyEvent>
                notifyDataSetChanged()
            }

        }
    }

}