package com.example.confinout.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.adapter.FavoriteAdapter
import com.example.confinout.database.EventDao
import com.example.confinout.database.EventDataBase
import com.example.confinout.model.MyEvent
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

class FavoriteFragment : Fragment() {

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var favoriteAdapter: FavoriteAdapter? = null
    var eventDao: EventDao? = null

    companion object {
        fun newInstance() : FavoriteFragment  = FavoriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fav_fragment, container, false)

        setupRecyclerView()

        eventDao = EventDataBase.getInstance(activity!!.application).eventDao()

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        displayFavoritesEvent()
    }

    /**
     * Sets the associated fragment recycler view.
     */
    private fun setupRecyclerView() {
        recyclerView = rootView!!.findViewById(R.id.favorite_list_view)
        favoriteAdapter = FavoriteAdapter(this)
        recyclerView!!.adapter = favoriteAdapter

        val viewManager : LinearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = viewManager
    }

    /**
     * Removes event in database
     */
    fun remove(event: MyEvent){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(eventDao!!.delete(event)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {

                override fun onComplete() {
                    println("deleting game from database")
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )

        Snackbar.make(
            recyclerView!!,
            "The event at ${event.place} has been remove from you favourites",
            Snackbar.LENGTH_SHORT
        ).show()
    }


    /**
     * Gets events from database and send them to the adapter so that to display it
     */
    private fun displayFavoritesEvent(){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(eventDao!!.findAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : ResourceSubscriber<MutableList<MyEvent>>() {

                override fun onNext(events : MutableList<MyEvent>) {
                    favoriteAdapter!!.bindViewModels(events)
                }

                override fun onComplete() {}

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }


    /**
     * Calls the fragment that helps to take picture
     */
    fun openTakerPicture(event: MyEvent){
        val takePictureFragment: TakePictureFragment = TakePictureFragment.newInstance(event)

        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, takePictureFragment)
            .addToBackStack(null)
            .commit()
    }
}