package com.magicapp.weatherapp.domain.repository

import com.magicapp.weatherapp.domain.util.Resource
import com.magicapp.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}