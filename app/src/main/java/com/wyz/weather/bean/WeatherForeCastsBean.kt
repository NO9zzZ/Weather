package com.wyz.weather.bean

import java.util.*

/**
 * @author zzZ
 * @date 2022/7/21 12:15
 * @describe
 * @e-mail:646888521@qq.com
 */
public class WeatherForeCastsBean {

    private var date: Date? = null
    private var week: String? = null
    private var dayweather: String? = null
    private var nightweather: String? = null
    private var daytemp: String? = null
    private var nighttemp: String? = null
    private var daywind: String? = null
    private var nightwind: String? = null
    private var daypower: String? = null
    private var nightpower: String? = null
    fun setDate(date: Date?) {
        this.date = date
    }

    fun getDate(): Date? {
        return date
    }

    fun setWeek(week: String?) {
        this.week = week
    }

    fun getWeek(): String? {
        return week
    }

    fun setDayweather(dayweather: String?) {
        this.dayweather = dayweather
    }

    fun getDayweather(): String? {
        return dayweather
    }

    fun setNightweather(nightweather: String?) {
        this.nightweather = nightweather
    }

    fun getNightweather(): String? {
        return nightweather
    }

    fun setDaytemp(daytemp: String?) {
        this.daytemp = daytemp
    }

    fun getDaytemp(): String? {
        return daytemp
    }

    fun setNighttemp(nighttemp: String?) {
        this.nighttemp = nighttemp
    }

    fun getNighttemp(): String? {
        return nighttemp
    }

    fun setDaywind(daywind: String?) {
        this.daywind = daywind
    }

    fun getDaywind(): String? {
        return daywind
    }

    fun setNightwind(nightwind: String?) {
        this.nightwind = nightwind
    }

    fun getNightwind(): String? {
        return nightwind
    }

    fun setDaypower(daypower: String?) {
        this.daypower = daypower
    }

    fun getDaypower(): String? {
        return daypower
    }

    fun setNightpower(nightpower: String?) {
        this.nightpower = nightpower
    }

    fun getNightpower(): String? {
        return nightpower
    }
}