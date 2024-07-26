package com.example.look_at_the_weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.look_at_the_weather.network.Constant.myApiKey
import com.example.look_at_the_weather.network.RetrofitInstance
import com.example.look_at_the_weather.network.model.WeatherResponseModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val weatherResult = MutableLiveData<ResponseCallback<WeatherResponseModel>>()
    val weatherResultLiveData: LiveData<ResponseCallback<WeatherResponseModel>> =
        weatherResult //exposed to UI

    fun getWeatherData(city: String) {
        weatherResult.value = ResponseCallback.Loading(true)

        viewModelScope.launch {
            try {
                val response = weatherApi.getData(myApiKey, city)
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        weatherResult.value = ResponseCallback.Success(data)
                    }
                } else {
                    weatherResult.value = ResponseCallback.Error("Something went wrong, please try again.")
                }
            } catch (e: Exception) {
                weatherResult.value = ResponseCallback.Error(e.message.toString())
            }
        }
    }
}