package com.magicapp.weatherapp.data.mappers

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.magicapp.weatherapp.data.remote.WeatherDataDto
import com.magicapp.weatherapp.data.remote.WeatherDto
import com.magicapp.weatherapp.domain.weather.WeatherData
import com.magicapp.weatherapp.domain.weather.WeatherInfo
import com.magicapp.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexWeatherData(
    val index: Int,
    val data: WeatherData
)



@SuppressLint("NewApi")
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { it ->
        it.value.map { it.data }
    }
}

@SuppressLint("NewApi")
fun WeatherDto.toWeatherInfo():WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}