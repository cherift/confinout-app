package com.example.confinout.response

import com.example.confinout.model.MyEvent
import com.google.gson.annotations.SerializedName

data class EventResponse (
    @SerializedName("count") val count : Int,
    @SerializedName("result") val events : MutableList<MyEvent>
)