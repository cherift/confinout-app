package com.example.confinout.remote

import com.example.confinout.response.EventResponse
import com.example.confinout.response.NotifResponse
import com.example.confinout.service.ConfinOutService
import io.reactivex.Single

class ConfinOutRemote {

    private val service : ConfinOutService = ConfinOutService()

    fun getEvents() : Single<EventResponse> = service.getEvents()
    fun getNotification(id: Int): Single<NotifResponse> = service.getNotifications(id)
    fun getComments(id: Int): Single<NotifResponse> = service.getComments(id)
    fun addComment(id: Int, message: String): Single<String> = service.addComment(id, message)
}