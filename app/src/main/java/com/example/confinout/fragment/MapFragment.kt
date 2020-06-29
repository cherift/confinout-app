package com.example.confinout.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.confinout.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style

class MapFragment :  Fragment(){

    var rootView : View? = null
    private var mapView: MapView? = null

    companion object {
        fun newInstance() : MapFragment  = MapFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Mapbox.getInstance(activity!!, getString(R.string.mapbox_access_token))

        rootView = inflater.inflate(R.layout.map_fragment, container, false)
        mapView = rootView!!.findViewById(R.id.mapView)

        mapView?.onCreate(savedInstanceState)

        mapView?.getMapAsync(object : OnMapReadyCallback {

            override fun onMapReady(mapboxMap: MapboxMap) {
                mapboxMap.cameraPosition = CameraPosition.Builder()
                    .target(LatLng(50.6108, 3.1381))
                    .zoom(10.0)
                    .tilt(20.0)
                    .build()
            }
        })
        

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            mapView?.onSaveInstanceState(outState)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
    }
}