package com.wyz.weather.bean

/**
 * @author zzZ
 * @date 2022/7/21 12:14
 * @describe
 * @e-mail:646888521@qq.com
 */
class WeatherForeBean {
    private var city: String? = null
    private var adcode: String? = null
    private var province: String? = null
    private var reporttime: String? = null
    private var casts: List<WeatherForeCastsBean>? = null
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

    fun setProvince(province: String?) {
        this.province = province
    }

    fun getProvince(): String? {
        return province
    }

    fun setReporttime(reporttime: String?) {
        this.reporttime = reporttime
    }

    fun getReporttime(): String? {
        return reporttime
    }

    fun setCasts(casts: List<WeatherForeCastsBean>?) {
        this.casts = casts
    }

    fun getCasts(): List<WeatherForeCastsBean>? {
        return casts
    }
}