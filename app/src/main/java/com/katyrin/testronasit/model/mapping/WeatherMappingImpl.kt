package com.katyrin.testronasit.model.mapping

import android.content.Context
import com.katyrin.testronasit.R
import com.katyrin.testronasit.model.data.WeatherDTO
import com.katyrin.testronasit.model.data.WeatherRequest
import com.katyrin.testronasit.model.storage.Storage
import kotlin.math.roundToInt

class WeatherMappingImpl(
    private val context: Context,
    private val storage: Storage
) : WeatherMapping {

    override fun mapWeatherRequestToWeatherDTO(weatherRequest: WeatherRequest): WeatherDTO =
        WeatherDTO(
            weatherRequest.name,
            getTemperatureString(weatherRequest.main.temp),
            weatherRequest.weather[0].description.replaceFirstChar { it.uppercase() },
            getWindString(weatherRequest),
            getPressureString(weatherRequest.main.pressure),
            getStringWithPercent(weatherRequest.main.humidity),
            getStringWithPercent(weatherRequest.clouds.all),
            weatherRequest.weather[0].icon
        )

    private fun getTemperatureString(temperature: Float): String =
        temperature.roundToInt().toString() + context.getString(R.string.degree)

    private fun getWindString(weatherRequest: WeatherRequest): String =
        "${weatherRequest.wind.speed} ${context.getString(getMeasureInt())}, " +
                getWindDirection(weatherRequest.wind.deg)

    private fun getMeasureInt(): Int =
        if (storage.isMetric()) R.string.meeter_per_second else R.string.miles_per_hour

    private fun getWindDirection(degree: Int): String =
        when (degree) {
            in 23..67 -> context.getString(R.string.north_east)
            in 68..112 -> context.getString(R.string.east)
            in 113..157 -> context.getString(R.string.south_east)
            in 158..202 -> context.getString(R.string.south)
            in 203..247 -> context.getString(R.string.south_west)
            in 248..292 -> context.getString(R.string.west)
            in 293..337 -> context.getString(R.string.north_west)
            else -> context.getString(R.string.north)
        }

    private fun getPressureString(pressure: Int): String =
        "$pressure ${context.getString(R.string.mm_hg_art)}"

    private fun getStringWithPercent(value: Int): String =
        "$value${context.getString(R.string.percent_char)}"
}