package com.wyz.weather.flownet

import com.wyz.weather.bean.WeatherBean
import com.wyz.weather.bean.WeatherForeBean
import com.wyz.weather.flownet.baseretrofit.BaseRepository
import com.wyz.weather.flownet.entity.ApiResponse

/**
 * @author zzZ
 * @date 2022/8/10 13:29
 * @describe
 * @e-mail:ywang450@volvocars.com
 */
class MyMainRepository : BaseRepository() {

    private val mService by lazy {
        MyRetrofitClient.service
    }

    suspend fun getWeather(city:String): ApiResponse<MutableList<WeatherBean>> {
        return executeHttp {
            mService.getWeather(city)
        }
    }

    suspend fun getWeatherFore(city:String): ApiResponse<MutableList<WeatherForeBean>> {
        return executeHttp {
            mService.getWeatherFore(city,"all")
        }
    }

}