package com.wyz.weather.flownet.baseretrofit

import android.util.Log
import com.wyz.weather.BuildConfig
import com.wyz.weather.flownet.entity.*
import kotlinx.coroutines.delay

/**
 * @author zzZ
 * @date 2022/8/10 12:55
 * @describe
 * @e-mail:ywang450@volvocars.com
 */
open class BaseRepository {
    val TAG = "BaseRepository"

    suspend fun <T> executeHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        //for test
        runCatching {
            block.invoke()
        }.onSuccess { data: ApiResponse<T> ->
            return handleHttpOk(data)
        }.onFailure { e ->
            Log.d(TAG,e.message.toString());
            return handleHttpError(e)
        }
        return ApiEmptyResponse()
    }

    /**
     * 非后台返回错误，捕获异常
     */
    private fun <T> handleHttpError(e: Throwable): ApiErrorResponse<T> {
        if (BuildConfig.DEBUG) e
        return ApiErrorResponse(e)
    }

    /**
     * status返回1
     */
    private fun <T> handleHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            data.getData()
            getHttpSuccessResponse(data)
        } else {
            ApiFailedResponse(data.status, data.info)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(response: ApiResponse<T>): ApiResponse<T> {
        var data = response.weatherData!!
        return if (data == null || data is List<*> && (data as List<*>).isEmpty()) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(data)
        }
    }

}