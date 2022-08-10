package com.wyz.weather.ui.main

import com.wyz.weather.bean.WeatherBean

/**
 * @author zzZ
 * @date 2022/8/9 23:00
 * @describe
 * @e-mail:646888521@qq.com
 */
class MainModel {
    var weatherBeanList: MutableList<WeatherBean>

    init {
        weatherBeanList = mutableListOf()
    }
}