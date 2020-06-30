package com.example.confinout.remote

import com.example.confinout.response.EventResponse
import com.example.confinout.service.ConfinOutService
import io.reactivex.Single

class ConfinOutRemote {

    private val service : ConfinOutService = ConfinOutService()

    fun getEvents() : Single<EventResponse> = service.getEvents()
}