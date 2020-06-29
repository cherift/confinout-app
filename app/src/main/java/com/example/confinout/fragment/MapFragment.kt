package com.example.confinout.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.confinout.R

class MapFragment :  Fragment(){

    var rootView : View? = null

    companion object {
        fun newInstance() : MapFragment  = MapFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.map_fragment, container, false)

        return rootView
    }
}