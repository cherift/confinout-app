package com.example.confinout.service

import com.example.confinout.response.EventResponse
import com.example.confinout.response.NotifResponse
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConfinOutService {

    @GET("events")
    fun getEvents() : Single<EventResponse>

    @GET("notifications/{event_id}")
    fun getNotifications(@Path("event_id") id: Int) : Single<NotifResponse>

    @GET("comments/{event_id}")
    fun getComments(@Path("event_id") id: Int) : Single<NotifResponse>

    @GET("comment/{event_id}/{message}")
    fun addComment(@Path("event_id") id: Int,
                   @Path("message") message: String) : Single<String>

    companion object {
        operator fun invoke() : ConfinOutService {
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor()

            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client : OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl("https://confinoutapi.herokuapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
                .create(ConfinOutService::class.java)
        }
    }
}