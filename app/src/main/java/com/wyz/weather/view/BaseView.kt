package com.wyz.weather.view

import android.content.Context
import com.uber.autodispose.AutoDisposeConverter

/**
 * @author zzZ
 * @date 2022/7/21 13:06
 * @describe
 * @e-mail:646888521@qq.com
 */
open interface BaseView {
    /**
     * 加载中
     */
    fun showLoading()

    /**
     * 隐藏加载loading
     */
    fun hideLoading()
    fun getContext(): Context?
    /**
     * 数据获取失败
     */
    //    void onError(Throwable throwable);
    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
    </T> */
    fun <T> bindAutoDispose(): AutoDisposeConverter<T>?
}
