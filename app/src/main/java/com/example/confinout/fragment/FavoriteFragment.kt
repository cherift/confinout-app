package com.example.confinout.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.confinout.R

class FavoriteFragment : Fragment() {

    var rootView : View? = null

    companion object {
        fun newInstance() : FavoriteFragment  = FavoriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fav_fragment, container, false)

        return rootView
    }
}