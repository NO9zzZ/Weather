package com.wyz.weather.retrofit

import java.io.Serializable

/**
 * @author zzZ
 * @date 2022/7/21 11:52
 * @describe
 * @e-mail:646888521@qq.com
 */
class Response<T> : Serializable {
    /**
     * {
     * "status": "1",
     * "count": "1",
     * "info": "OK",
     * "infocode": "10000",
     * "lives": ["0", {
     *  "province": "北京",
     *  "city": "东城区",
     *  "adcode": "110101",
     *  "weather": "晴",
     *  "temperature": "28",
     * "winddirection": "西南",
     *  "windpower": "≤3",
     * "humidity": "62",
     * "reporttime": "2022-07-21 10:40:54"
     *  }]
     *  }
     */
    private var status: String? = null
    private var count: String? = null
    private var lives: T? = null
    private var forecasts: T? = null
    private var info: String? = null
    private var infocode: String? = null
    fun setTotal(status: String) {
        this.status = status
    }

    fun getStatus(): String? {
        return status
    }

    fun setLives(lives: T) {
        this.lives = lives
    }

    fun getLives(): T? {
        return lives
    }

    fun setForecasts(forecasts: T) {
        this.forecasts = forecasts
    }

    fun getForecasts(): T? {
        return forecasts
    }

    fun setCount(count: String?) {
        this.count = count
    }

    fun getCount(): String? {
        return count
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
}

