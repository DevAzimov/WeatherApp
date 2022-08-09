package com.magicapp.weatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.magicapp.weatherapp.data.mappers.toWeatherInfo
import com.magicapp.weatherapp.data.remote.WeatherApi
import com.magicapp.weatherapp.domain.repository.WeatherRepository
import com.magicapp.weatherapp.domain.util.Resource
import com.magicapp.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}