package com.wyz.weather.retrofit

import android.content.Intent
import com.wyz.weather.util.ToastUtil
import com.wyz.weather.view.BaseView
import io.reactivex.subscribers.DisposableSubscriber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author zzZ
 * @date 2022/7/21 13:05
 * @describe
 * @e-mail:646888521@qq.com
 */
abstract class SubscriberFore<T>(view: BaseView?) :
    DisposableSubscriber<Response<T?>?>() {
    private val mView: BaseView?
    private val SUCCESS_CODE = "1"
    override fun onStart() {
        super.onStart()
        if (mView != null) mView.showLoading()
    }

    override fun onNext(t: Response<T?>?) {
        if (SUCCESS_CODE == t?.getStatus()) {
            onHandleSuccess(t?.getForecasts(), t?.getInfo())
        } else {
            onHandleError(t?.getStatus(), t?.getInfo())
        }
    }

    override fun onError(e: Throwable) {
        hideLoading()
        if (e is SocketTimeoutException || e is UnknownHostException || e is ConnectException) {
            ToastUtil.showCenterToast(mView?.getContext(),"网络中断，请检查您的网络状态", ToastUtil.ToastType.ERROR)
            onException()
        } else {
            ToastUtil.showCenterToast(mView?.getContext(),e?.message, ToastUtil.ToastType.ERROR)
        }
    }

    override fun onComplete() {
        hideLoading()
    }

    abstract fun onHandleSuccess(result: T?, message: String?)
    fun onHandleError(code: String?, message: String?) {
        ToastUtil.showCenterToast(mView?.getContext(),message, ToastUtil.ToastType.ERROR)
    }

    fun onException() {}
    private fun hideLoading() {
        if (mView != null) mView.hideLoading()
    }

    init {
        mView = view
    }
}
