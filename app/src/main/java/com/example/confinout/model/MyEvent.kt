package com.example.confinout.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.net.Inet4Address
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "events")
data class MyEvent(
    @PrimaryKey
    @SerializedName("id") var id : Int,
    @SerializedName("place") var place: String,
    @SerializedName("address") var address: String,
    @SerializedName("price") var price: Float,
    @SerializedName("date") var date: String,
    @SerializedName("description") var description: String,
    @SerializedName("typeid") var typeid: Int,
    @SerializedName("link") var link: String,
    @SerializedName("number") var number: Int,
    @SerializedName("longitude") var longitude: Float,
    @SerializedName("latitude") var latitude: Float,
    @SerializedName("inside") var inside: Boolean,
    @SerializedName("available") var available: Boolean,
    @SerializedName("handicap") var handicap: Boolean
)