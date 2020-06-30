package com.example.confinout.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.confinout.R
import com.example.confinout.model.MyEvent
import kotlinx.android.synthetic.main.event_details.*
import org.w3c.dom.Text

class EventDetailsFragments(val event: MyEvent) : Fragment() {

    var rootView : View? = null
    var place: TextView? = null
    var price: TextView? = null
    var date: TextView? = null
    var is_inside: ImageView? = null
    var is_handicap: ImageView? = null
    var description: TextView? = null
    var comment: Button? = null


    companion object {
        fun newInstance(event: MyEvent) : EventDetailsFragments  = EventDetailsFragments(event)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.event_details, container, false)

        place = rootView!!.findViewById(R.id.details_place)
        price = rootView!!.findViewById(R.id.details_price)
        is_inside = rootView!!.findViewById(R.id.is_inside)
        is_handicap = rootView!!.findViewById(R.id.is_handicap)
        description = rootView!!.findViewById(R.id.details_description)
        date = rootView!!.findViewById(R.id.details_date)
        comment = rootView!!.findViewById(R.id.comment)

        place!!.text = event.place
        price!!.text = event.price.toString()
        date!!.text = event.date
        description!!.text = event.description
        is_handicap!!.visibility = if (event.handicap) View.VISIBLE else View.GONE
        is_inside!!.setImageResource(if (event.inside) R.drawable.inside else R.drawable.outside)

        comment!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })

        return rootView

    }
}