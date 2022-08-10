package com.wyz.weather.ui.splash

/**
 * @author zzZ
 * @date 2022/8/9 20:57
 * @describe
 * @e-mail:646888521@qq.com
 */
class SplashModel {

    var citiesList: Array<String>
    var codepms = -1;

    init {
        citiesList = arrayOf(
            "110000"/*北京*/,
            "310000"/*上海*/,
            "440100"/*广州*/,
            "440300"/*深圳*/,
            "320500"/*苏州*/,
            "210100"/*沈阳*/
        )
    }

    fun cities(): Array<String> {
        return citiesList
    }

    fun addCity(city: String) {
        citiesList.plus(city)
    }

    fun setCity(cities: Array<String>) {
        citiesList = cities
    }

    fun codepms(): Int {
        return codepms
    }

    fun setCodePms(code: Int) {
        codepms = code
    }


}