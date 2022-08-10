package com.wyz.weather.bean

/**
 * @author zzZ
 * @date 2022/7/21 12:42
 * @describe
 * @e-mail:646888521@qq.com
 */
class LocationBean {
    private var status: String? = null
    private var info: String? = null
    private var infocode: String? = null
    private var province: String? = null
    private var city: String? = null
    private var adcode: String? = null
    private var rectangle: String? = null
    fun setStatus(status: String?) {
        this.status = status
    }

    fun getStatus(): String? {
        return status
    }

    fun setInfo(info: String?) {
        this.info = info
    }

    fun getInfo(): String? {
        return info
    }

    fun setInfocode(infocode: String?) {
        this.infocode = infocode
    }

    fun getInfocode(): String? {
        return infocode
    }

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

    fun setRectangle(rectangle: String?) {
        this.rectangle = rectangle
    }

    fun getRectangle(): String? {
        return rectangle
    }
}