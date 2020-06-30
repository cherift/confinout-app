package com.example.confinout.model

import com.google.gson.annotations.SerializedName

data class Notif(
    @SerializedName("date") val date: String,
    @SerializedName("message") val message: String
)