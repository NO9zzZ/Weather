package com.wyz.weather.flownet.baseretrofit

import android.util.Log
import com.wyz.weather.flownet.entity.*


fun <T> ApiResponse<T>.parseData(listenerBuilder: ResultBuilder<T>.() -> Unit) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    when (this) {
        is ApiSuccessResponse -> {
            listener.onSuccess(this.response)
        }
        is ApiEmptyResponse -> listener.onDataEmpty()
        is ApiFailedResponse -> listener.onFailed(this.status, this.info)
        is ApiErrorResponse -> listener.onError(this.throwable)
    }
    listener.onComplete()
}

class ResultBuilder<T> {
    var onSuccess: (data: T?) -> Unit = {}
    var onDataEmpty: () -> Unit = {}
    var onFailed: (errorCode: String?, errorMsg: String?) -> Unit = { _, errorMsg ->
        errorMsg?.let { /*异常 it*/ }
    }
    var onError: (e: Throwable) -> Unit = { e ->
        e.message?.let {/*异常 it*/ }
    }
    var onComplete: () -> Unit = {}
}