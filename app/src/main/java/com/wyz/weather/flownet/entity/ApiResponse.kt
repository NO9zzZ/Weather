package com.wyz.weather.flownet.entity

import java.io.Serializable

/**
 * @author zzZ
 * @date 2022/8/10 12:52
 * @describe
 * @e-mail:ywang450@volvocars.com
 */
open class ApiResponse<T>(
    open var status: String? = null,
    open var count: String? = null,
    open var lives: T? = null,
    open var forecasts: T? = null,
    open var info: String? = null,
    open var infocode: String? = null,
    open val error: Throwable? = null,
    open var weatherData: T? = null,

    ) : Serializable {
    val isSuccess: Boolean
        get() = status?.toInt() == 1

    fun getData(){
        if (lives != null)
            weatherData =  lives
        else
            weatherData =  forecasts
    }

    override fun toString(): String {
        return "ApiResponse(weatherData=$weatherData, status=$status)"
    }


}

data class ApiSuccessResponse<T>(val response: T) : ApiResponse<T>(forecasts = response)

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiFailedResponse<T>(override var status: String?, override var info: String?) :
    ApiResponse<T>(status = status, info = info)

data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>(error = throwable)
