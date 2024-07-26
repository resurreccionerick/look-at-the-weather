package com.example.look_at_the_weather.network.model

data class WeatherResponseModel(
    val current: Current,
    val location: Location
)