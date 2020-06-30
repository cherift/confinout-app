package com.example.confinout.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.confinout.R
import com.example.confinout.adapter.NotifAdapter
import com.example.confinout.model.Notif
import com.example.confinout.remote.ConfinOutRemote
import com.example.confinout.response.NotifResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CommentsFragment(val eventid: Int) : Fragment(), MessageView{

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var notifAdapter : NotifAdapter? = null
    var validButton : Button? = null
    var editText: EditText? = null

    companion object {
        fun newInstance(eventid: Int) : CommentsFragment  = CommentsFragment(eventid)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.comments_fragment, container, false)

        setupRecyclerView()

        validButton = rootView!!.findViewById(R.id.validButton)
        editText = rootView!!.findViewById(R.id.editText)

        validButton!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (editText!!.text.toString() != ""){
                    addComment(editText!!.text.toString())
                }
            }

        })

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchEventComment(eventid)
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
     * Search for comments associated to an event
     *
     * @param eventid: the event id
     */
    private fun searchEventComment(eventid: Int){

        val compositeDisposable : CompositeDisposable = CompositeDisposable()
        compositeDisposable.clear()

        val confinoutRemote = ConfinOutRemote()

        compositeDisposable.add(confinoutRemote.getComments(eventid)
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


    /**
     * Adds a comment in an event
     *
     * @param message: the message as comment
     */
    private fun addComment(message: String){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()
        compositeDisposable.clear()

        val confinoutRemote = ConfinOutRemote()

        compositeDisposable.add(confinoutRemote.addComment(eventid, message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<String>() {
                override fun onSuccess(response: String) {
                    println("Confinout-message : ${response}")
                }

                override fun onError(e: Throwable) {
                    println("confinout-error: ${e.message} ${e.cause?.message}")
                }
            })
        )

        activity!!.supportFragmentManager.popBackStack()
    }

    private fun displayNotifs(list: MutableList<Notif>){
        notifAdapter!!.bindViewModels(list)
    }
}