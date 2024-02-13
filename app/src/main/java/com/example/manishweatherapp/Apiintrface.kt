package com.example.manishweatherapp

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Apiintrface {
    @GET("weather")

    fun getWeatherData(
        @Query("q") city:String,
        @Query("appid") appid:String,
        @Query("units") units:String
    ): retrofit2.Call<Manishweather>
}