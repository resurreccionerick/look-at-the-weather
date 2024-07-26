package com.example.look_at_the_weather.viewmodel

import com.example.look_at_the_weather.network.model.WeatherResponseModel

/**
 * T is a generic type parameter.
 * The out modifier makes the T covariant, meaning ResponseCallback<T> can be used in any situation where a ResponseCallback of a supertype of T is expected.
 * This is useful for ensuring type safety when dealing with subtypes.
 * T extends WeatherResponseModel, ensuring that only subclasses of WeatherResponseModel can be used as the type parameter.**/
sealed class ResponseCallback<out T : WeatherResponseModel> {
    data class Success<out T : WeatherResponseModel>(val data: T) : ResponseCallback<T>()
    data class Error(val msg: String) : ResponseCallback<Nothing>()
    data class Loading(val loading: Boolean) : ResponseCallback<Nothing>()
}