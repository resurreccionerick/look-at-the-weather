package com.example.look_at_the_weather.network

import com.example.look_at_the_weather.network.model.WeatherResponseModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface ApiService {

    @GET("v1/current.json")
    suspend fun getData(
        @Query("key") myKey: String,
        @Query("q") cityName: String
    ): Response<WeatherResponseModel>
}