package com.katyrin.testronasit.model.data

data class WeatherRequest(
    var coord: Coord,
    var weather: Array<Weather>,
    var main: Main,
    var wind: Wind,
    var clouds: Clouds,
    var name: String,
    var dt: Long,
    var daily: Array<Daily>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherRequest

        if (coord != other.coord) return false
        if (!weather.contentEquals(other.weather)) return false
        if (main != other.main) return false
        if (wind != other.wind) return false
        if (clouds != other.clouds) return false
        if (name != other.name) return false
        if (dt != other.dt) return false
        if (!daily.contentEquals(other.daily)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coord.hashCode()
        result = 31 * result + weather.contentHashCode()
        result = 31 * result + main.hashCode()
        result = 31 * result + wind.hashCode()
        result = 31 * result + clouds.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + dt.hashCode()
        result = 31 * result + daily.contentHashCode()
        return result
    }
}
