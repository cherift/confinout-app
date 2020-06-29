package com.example.confinout.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.adapter.LocationRecyclerViewAdapter
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyValue
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource

class MapFragment :  Fragment(), OnMapReadyCallback{

    var rootView : View? = null
    var mapView: MapView? = null
    var coordinates: MutableList<LatLng>? = null
    var featureCollection: FeatureCollection? = null
    val SYMBOL_ICON_ID : String = "SYMBOL_ICON_ID"
    val SOURCE_ID : String = "SOURCE_ID"
    val LAYER_ID : String = "LAYER_ID"

    companion object {
        fun newInstance() : MapFragment  = MapFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(activity!!, getString(R.string.mapbox_access_token))

        setupMarkerList()

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

        mapView?.getMapAsync(this)

        return rootView
    }

    /**
     * Sets the list of coordinates for makers
     */
    private fun setupMarkerList(){
        coordinates = mutableListOf(
            LatLng(-34.6054099, -58.363654800000006),
            LatLng(-34.6041508, -58.38555650000001),
            LatLng(-34.6114412, -58.37808899999999),
            LatLng(-34.6097604, -58.382064000000014),
            LatLng(-34.596636, -58.373077999999964),
            LatLng(-34.590548, -58.38256609999996),
            LatLng(-34.5982127, -58.38110440000003)
        )


    }

    /**
     * Calls functions to initialize style and data in the map
     * when it is ready.
     */
    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setStyle(Style.DARK, object : Style.OnStyleLoaded {
            override fun onStyleLoaded(style: Style) {
                /*initFeatureCollection()
                initMarkerIcons(style)
                initRecyclerView()*/
            }
        })
    }

    /**
     * Sets the markers according to the values in coordinates list.
     */
    private fun initFeatureCollection(){
        var featureList : MutableList<Feature> = mutableListOf()
        featureCollection?.let{
            coordinates!!.forEach { coord ->
                featureList.add(Feature.fromGeometry(Point.fromLngLat(coord.longitude, coord.latitude)))
            }
            featureCollection = FeatureCollection.fromFeatures(featureList)
        }
    }

    /**
     * Sets the recycler view
     */
    private fun initRecyclerView(){
        val recyclerView: RecyclerView = rootView!!.findViewById(R.id.rv_on_top_of_map)
        //var locationAdapter : LocationRecyclerViewAdapter = ()
    }

    private fun initMarkerIcons(loadedMapStyle: Style) {
        loadedMapStyle.addImage(SYMBOL_ICON_ID, BitmapFactory.decodeResource(resources, R.drawable.favorite))
        loadedMapStyle.addSource(GeoJsonSource(SOURCE_ID, featureCollection))
        loadedMapStyle.addLayer(SymbolLayer(LAYER_ID, SOURCE_ID).withProperties(
            PropertyFactory.iconImage(SYMBOL_ICON_ID),
            PropertyFactory.iconAllowOverlap(true),
            PropertyFactory.iconOffset(arrayOf<Float>(0f, -4f))
        ))
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