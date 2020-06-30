package com.example.confinout.response

import com.example.confinout.model.Notif
import com.google.gson.annotations.SerializedName

data class NotifResponse (
    @SerializedName("count") var count: Int,
    @SerializedName("result") var result: MutableList<Notif>
)