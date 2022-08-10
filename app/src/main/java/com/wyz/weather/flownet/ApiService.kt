package com.wyz.weather.flownet

import com.wyz.weather.BuildConfig
import com.wyz.weather.bean.LocationBean
import com.wyz.weather.bean.WeatherBean
import com.wyz.weather.bean.WeatherForeBean
import com.wyz.weather.flownet.entity.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("key: "+ BuildConfig.AMapKEY)
    @GET("weather/weatherInfo")
    suspend fun getWeather(@Query("city") city: String?): ApiResponse<MutableList<WeatherBean>>

    @Headers("key: "+ BuildConfig.AMapKEY)
    @GET("weather/weatherInfo")
    suspend fun getWeatherFore(@Query("city") city: String?,@Query("extensions") extensions: String?): ApiResponse<MutableList<WeatherForeBean>>

    companion object {
        const val BASE_URL = "https://restapi.amap.com/v3/"
    }
}