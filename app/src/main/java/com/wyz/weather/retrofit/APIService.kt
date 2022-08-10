package com.wyz.weather.retrofit

import com.wyz.weather.bean.LocationBean
import com.wyz.weather.bean.WeatherBean
import com.wyz.weather.bean.WeatherForeBean
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * @author zzZ
 * @date 2022/7/21 11:49
 * @describe
 * @e-mail:646888521@qq.com
 */

open interface APIService {

    //查询今天天气
    @GET("weather/weatherInfo")
    fun GetWeather(@Query("city") city: String?): Flowable<Response<MutableList<WeatherBean>?>?>

    //查询未来7天预告天气
    @GET("weather/weatherInfo")
    fun GetWeatherFore(@Query("city") city: String?,@Query("extensions") extensions: String?): Flowable<Response<MutableList<WeatherForeBean?>?>?>

    //ip定位
    @GET("ip")
    fun GetLocation(): Flowable<LocationBean?>

}

