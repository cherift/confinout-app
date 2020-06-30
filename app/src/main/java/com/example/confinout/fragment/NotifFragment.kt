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
import com.example.confinout.adapter.NotifAdapter
import com.example.confinout.database.EventDao
import com.example.confinout.database.EventDataBase
import com.example.confinout.model.MyEvent
import com.example.confinout.model.Notif
import com.example.confinout.remote.ConfinOutRemote
import com.example.confinout.response.EventResponse
import com.example.confinout.response.NotifResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

class NotifFragment : Fragment(), MessageView {

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var notifAdapter : NotifAdapter? = null
    var eventDao: EventDao? = null

    companion object {
        var listNotifs: MutableList<Notif> = mutableListOf()

        fun newInstance() : NotifFragment  = NotifFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.notif_fragment, container, false)

        setupRecyclerView()

        eventDao = EventDataBase.getInstance(activity!!.application).eventDao()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchFavortiteEvents()
    }

    /**
     * Sets the associated fragment recycler view.
     */
    private fun setupRecyclerView() {
        recyclerView = rootView!!.findViewById(R.id.notif_list_view)
        notifAdapter = NotifAdapter(this)
        recyclerView!!.adapter = notifAdapter

        val viewManager : LinearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = viewManager
    }


    /**
     * Searches for favorite events and add in eventList variable
     *
     */
    private fun searchFavortiteEvents(){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(eventDao!!.findAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : ResourceSubscriber<MutableList<MyEvent>>() {

                override fun onNext(events : MutableList<MyEvent>) {
                    updateListNotifs(events)
                }

                override fun onComplete() {}

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }

    /**
     * Updaes list notifs
     */
    private fun updateListNotifs(eventList: MutableList<MyEvent>){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()
        compositeDisposable.clear()
        listNotifs.clear()

        eventList.forEach { event ->

            val confinoutRemote = ConfinOutRemote()

            compositeDisposable.add(confinoutRemote.getNotification(event.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NotifResponse>() {
                    override fun onSuccess(response: NotifResponse) {
                        displayNotifs(response.result)
                    }

                    override fun onError(e: Throwable) {
                        println("confinout-error: ${e.message} ${e.cause?.message}")
                    }
                })
            )

        }
    }

    private fun displayNotifs(list: MutableList<Notif>){
        listNotifs.addAll(list)
        notifAdapter!!.bindViewModels(listNotifs)
    }
}