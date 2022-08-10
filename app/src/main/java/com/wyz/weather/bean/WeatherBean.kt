package com.wyz.weather.bean

/**
 * @author zzZ
 * @date 2022/7/21 11:56
 * @describe
 * @e-mail:646888521@qq.com
 */
class WeatherBean {
    private var province: String? = null
    private var city: String? = null
    private var adcode: String? = null
    private var weather: String? = null
    private var temperature: String? = null
    private var winddirection: String? = null
    private var windpower: String? = null
    private var humidity: String? = null
    private var reporttime: String? = null
    fun setProvince(province: String?) {
        this.province = province
    }

    fun getProvince(): String? {
        return province
    }

    fun setCity(city: String?) {
        this.city = city
    }

    fun getCity(): String? {
        return city
    }

    fun setAdcode(adcode: String?) {
        this.adcode = adcode
    }

    fun getAdcode(): String? {
        return adcode
    }

    fun setWeather(weather: String?) {
        this.weather = weather
    }

    fun getWeather(): String? {
        return weather
    }

    fun setTemperature(temperature: String?) {
        this.temperature = temperature
    }

    fun getTemperature(): String? {
        return temperature
    }

    fun setWinddirection(winddirection: String?) {
        this.winddirection = winddirection
    }

    fun getWinddirection(): String? {
        return winddirection
    }

    fun setWindpower(windpower: String?) {
        this.windpower = windpower
    }

    fun getWindpower(): String? {
        return windpower
    }

    fun setHumidity(humidity: String?) {
        this.humidity = humidity
    }

    fun getHumidity(): String? {
        return humidity
    }

    fun setReporttime(reporttime: String?) {
        this.reporttime = reporttime
    }

    fun getReporttime(): String? {
        return reporttime
    }
}