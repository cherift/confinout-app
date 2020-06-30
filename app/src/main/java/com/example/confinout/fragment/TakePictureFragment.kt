package com.example.confinout.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.model.MyEvent
import kotlinx.android.synthetic.main.event_card_view.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TakePictureFragment(val event: MyEvent) : Fragment() {

    var rootView : View? = null
    var placename : TextView? = null
    var takePicture: Button? = null
    var recyclerView: RecyclerView?= null
    val REQUEST_IMAGE_CAPTURE = 1

    companion object{
        fun newInstance(event: MyEvent) = TakePictureFragment(event)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.picture_fragment, container, false)

        placename = rootView!!.findViewById(R.id.placename)
        takePicture = rootView!!.findViewById(R.id.takePicture)

        placename!!.text = event.place

        /*takePicture!!.setOnClickListener(object  : View.OnClickListener {
            override fun onClick(v: View?) {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }

        })*/

        return rootView
    }

}