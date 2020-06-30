package com.example.confinout.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.adapter.EventAdapter
import com.example.confinout.database.EventDao
import com.example.confinout.database.EventDataBase
import com.example.confinout.model.MyEvent
import com.example.confinout.remote.ConfinOutRemote
import com.example.confinout.response.EventResponse
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class EventsFragment : Fragment() {

    var rootView : View? = null
    var searchView : SearchView? = null
    var recyclerView : RecyclerView? = null
    var eventAdapter: EventAdapter? = null
    var eventDao: EventDao? = null

    companion object {
        fun newInstance() : EventsFragment  = EventsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.event_fragment, container, false)
        searchView = rootView!!.findViewById(R.id.search_view)

        setupRecyclerView()

        eventDao = EventDataBase.getInstance(activity!!.application).eventDao()

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val myHandler: Handler = Handler()
        myHandler.postDelayed(Runnable {
            initListEvents()
        }, 300)

        filter()
    }

    /**
     * Sets the associated fragment recycler view.
     */
    private fun setupRecyclerView() {
        recyclerView = rootView!!.findViewById(R.id.events_list_view)
        eventAdapter = EventAdapter(this)
        recyclerView!!.adapter = eventAdapter

        val viewManager : LinearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = viewManager
    }


    /**
     * Search for events by using confinout api
     */
    private fun initListEvents(){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        val confinoutRemote = ConfinOutRemote()

        compositeDisposable.clear()

        compositeDisposable.add(confinoutRemote.getEvents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<EventResponse>() {
                override fun onSuccess(response: EventResponse) {
                    displayEvents(response.events)
                }

                override fun onError(e: Throwable) {
                    println("confinout-error: ${e?.message}")
                }
            })
        )
    }

    /**
     * Displays in revycler view the events founded
     */
    private fun displayEvents(listEvents: MutableList<MyEvent>){
        eventAdapter!!.bindViewModels(listEvents)
    }


    /**
     * filter adapter view form search
     */
    private fun filter(){
        searchView!!.setOnQueryTextListener( object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    eventAdapter!!.filter.filter(query as CharSequence)
                }
                searchView!!.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }

    fun addEvent(event: MyEvent){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(eventDao!!.insert(event)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {

                override fun onComplete() {
                    println("The event at ${event.place} has been added to the database.")
                }

                override fun onError(e: Throwable) {
                    println("Confinout-error : ${e.message}")
                }
            })
        )

        Snackbar.make(
            recyclerView!!,
            "The event at ${event.place} has been added in your favourites",
            Snackbar.LENGTH_SHORT
        ).show()
    }


    /**
     * Open new fragment to see the details of and event
     *
     * @param event: the event to see a details
     */
    fun getDetails(event: MyEvent){
        val detailsFragments: EventDetailsFragments = EventDetailsFragments.newInstance(event)

        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailsFragments)
            .addToBackStack(null)
            .commit()
    }
}