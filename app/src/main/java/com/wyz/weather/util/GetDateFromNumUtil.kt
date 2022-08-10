package com.wyz.weather.util

/**
 * @author zzZ
 * @date 2022/7/21 17:35
 * @describe
 * @e-mail:646888521@qq.com
 */
class GetDateFromNumUtil {
    fun getDay(num:Int): String {
        return when (num) {
            1 -> "周一"
            2 -> "周二"
            3 -> "周三"
            4 -> "周四"
            5 -> "周五"
            6 -> "周六"
            7 -> "周日"
            else -> ""
        }
    }
}