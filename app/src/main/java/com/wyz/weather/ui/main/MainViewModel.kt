package com.wyz.weather.ui.main

import androidx.lifecycle.ViewModel
import com.wyz.weather.bean.WeatherBean
import com.wyz.weather.bean.WeatherForeBean
import com.wyz.weather.flownet.MyMainRepository
import com.wyz.weather.flownet.entity.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @author zzZ
 * @date 2022/8/10 13:34
 * @describe
 * @e-mail:ywang450@volvocars.com
 */
class MainViewModel: ViewModel() {
    private val repository by lazy { MyMainRepository() }

    private val _rvState = MutableStateFlow<ApiResponse<MutableList<WeatherForeBean>>>(ApiResponse())
    val rvState: StateFlow<ApiResponse<MutableList<WeatherForeBean>>> = _rvState.asStateFlow()

    suspend fun getWeatherFore(city:String) :ApiResponse<MutableList<WeatherForeBean>>{
        return repository.getWeatherFore(city)
    }

    suspend fun getWeather(city:String): ApiResponse<MutableList<WeatherBean>> {
        return repository.getWeather(city)
    }

}