package com.wyz.weather.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.wyz.weather.bean.WeatherBean
import com.wyz.weather.bean.WeatherForeBean
import com.wyz.weather.flownet.MyMainRepository
import com.wyz.weather.flownet.entity.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author zzZ
 * @date 2022/8/10 13:34
 * @describe
 * @e-mail:ywang450@volvocars.com
 */
class MainViewModel(application: Application) : AndroidViewModel(application), KoinComponent {
    private val repository by lazy { MyMainRepository() }
    private val mModel: MainModel by inject()

    fun getList() = mModel.weatherBeanList
    fun getCities() = mModel.cities
    fun setCities(cities: Array<String>) {
        mModel.cities = cities
    }

    private val _rvState =
        MutableStateFlow<ApiResponse<MutableList<WeatherForeBean>>>(ApiResponse())
    val rvState: StateFlow<ApiResponse<MutableList<WeatherForeBean>>> = _rvState.asStateFlow()

    suspend fun getWeatherFore(city: String): ApiResponse<MutableList<WeatherForeBean>> {
        return repository.getWeatherFore(city)
    }

    suspend fun getWeather(city: String): ApiResponse<MutableList<WeatherBean>> {
        return repository.getWeather(city)
    }

    //根据cities排序mViewModel.getList()
    fun sortList() {
        var list = sort(mModel.weatherBeanList!!)
        mModel.weatherBeanList.clear()
        mModel.weatherBeanList.addAll(list)
    }

    fun sort(list: MutableList<WeatherBean>): MutableList<WeatherBean> {
        var map: MutableMap<String, WeatherBean> = mutableMapOf()
        var sortList: MutableList<WeatherBean> = mutableListOf()
        for (i in 0 until list.size) {
            map.put(list[i].getAdcode().toString(), list[i])
        }
        for (j in 0 until mModel.cities.size) {
//            Log.d(TAG,map.get(cities[j])!!.getCity().toString())
            sortList?.add(map.get(mModel.cities[j])!!)
        }
        return sortList
    }

}