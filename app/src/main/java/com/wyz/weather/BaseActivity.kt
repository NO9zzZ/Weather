package com.wyz.weather

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.wyz.weather.databinding.ActivityMainBinding
import com.wyz.weather.dialog.LoadingDialog
import com.wyz.weather.util.NetworkUtil
import com.wyz.weather.util.ToastUtil
import com.wyz.weather.view.BaseView
import com.wyz.weather.view.IUiView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * @author zzZ
 * @date 2022/7/21 11:08
 * @describe
 * @e-mail:646888521@qq.com
 */
abstract class BaseActivity : AppCompatActivity(),BaseView,IUiView {
    var pd: LoadingDialog? = null
    var mObservableTransformer: ObservableTransformer<*, *>? = null
    private var showLoading = true
    var TAG = BaseActivity::class.java.simpleName
    abstract fun initView()
    abstract fun loadData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //竖屏

        TAG = javaClass.simpleName
        init()
        initView()
        loadData()
    }

    /**
     * 控制是否使用沉浸式状态栏
     */
    fun immersionStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 沉浸式状态栏
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // 状态栏改为透明
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun init() {
        pd = LoadingDialog.Builder(this).setMessage("加载中...").create()
        mObservableTransformer =
            ObservableTransformer<Any?, Any?> { observable ->
                observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .doOnSubscribe(object : Consumer<Disposable?> {
                        override fun accept(t: Disposable?) {
                            if (NetworkUtil.isNetworkAvailable(this@BaseActivity)) {
                                if (showLoading) {
                                    if (pd != null && !pd!!.isShowing) {
                                        pd!!.show()
                                    }
                                }
                            } else {
                                ToastUtil.showShortToast(getContext(),"网络连接异常，请检查网络")
                            }
                        }

                    })
                    .observeOn(AndroidSchedulers.mainThread())
            }
    }

    fun setLoadingFlag(show: Boolean) {
        showLoading = show
    }

    override fun onStop() {
        super.onStop()
        if (pd != null && pd!!.isShowing()) {
            pd!!.dismiss()
        }
    }

    fun start(clazz: Class<*>?) {
        startActivity(Intent(this, clazz))
    }

    fun start(intent: Intent?) {
        startActivity(intent)
    }

    fun toast(string: String?) {
        ToastUtil.showLongToast(getContext(),string)
    }

    @JvmName("getPd1")
    fun getPd(): LoadingDialog? {
        return pd
    }

    @JvmName("setPd1")
    fun setPd(pd: LoadingDialog?) {
        this.pd = pd
    }

    fun showTip(message: String?) {
        ToastUtil.showShortToast(getContext(),message)
    }


    private fun isTranslucentOrFloating(): Boolean {
        var isTranslucentOrFloating = false
        try {
            val styleableRes = Class.forName("com.android.internal.R\$styleable")
                .getField("Window")[null] as IntArray
            val ta = obtainStyledAttributes(styleableRes)
            val m =
                ActivityInfo::class.java.getMethod(
                    "isTranslucentOrFloating",
                    TypedArray::class.java
                )
            m.isAccessible = true
            isTranslucentOrFloating = m.invoke(null, ta) as Boolean
            m.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isTranslucentOrFloating
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            return
        }
        super.setRequestedOrientation(requestedOrientation)
    }

    override fun onPause() {
        Log.i(TAG, "onPause")
        super.onPause()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        super.onDestroy()
    }

    /**
     * 重写getResources()方法，让APP的字体不受系统设置字体大小影响
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.fontScale = 1.0f
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
    </T> */
    override fun <T> bindAutoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(
            AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY)
        )
    }

    private var progressDialog: ProgressDialog? = null

    override fun showLoading() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }
}
