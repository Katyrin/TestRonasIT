package com.katyrin.testronasit.model.data

data class Daily(
    var dt: Long,
    var pressure: Int,
    var humidity: Int,
    var wind_speed: Float,
    var weather: Array<Weather>,
    var temp: Temp
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Daily

        if (dt != other.dt) return false
        if (pressure != other.pressure) return false
        if (humidity != other.humidity) return false
        if (wind_speed != other.wind_speed) return false
        if (!weather.contentEquals(other.weather)) return false
        if (temp != other.temp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dt.hashCode()
        result = 31 * result + pressure
        result = 31 * result + humidity
        result = 31 * result + wind_speed.hashCode()
        result = 31 * result + weather.contentHashCode()
        result = 31 * result + temp.hashCode()
        return result
    }
}
