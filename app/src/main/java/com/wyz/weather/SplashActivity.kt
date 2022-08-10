package com.wyz.weather

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.permissionx.guolindev.PermissionX
import com.wyz.weather.bean.LocationBean
import com.wyz.weather.databinding.ActivitySplashBinding
import com.wyz.weather.retrofit.SubscriberLocation
import com.wyz.weather.retrofit.RetrofitClient
import com.wyz.weather.retrofit.RxScheduler
import com.wyz.weather.util.ToastUtil

/**
 * @author zzZ
 * @date 2022/7/21 11:03
 * @describe 启动页
 * @e-mail:646888521@qq.com
 */
class SplashActivity : BaseActivity() {
    lateinit var mBinding: ActivitySplashBinding
    var cities: Array<String> = arrayOf(
        "110000"/*北京*/,
        "310000"/*上海*/,
        "440100"/*广州*/,
        "440300"/*深圳*/,
        "320500"/*苏州*/,
        "210100"/*沈阳*/
    )

    override fun initView() {
        immersionStatusBar()
    }

    override fun loadData() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        Handler().postDelayed({
            initPermission()
        }, 1000)
    }

    private fun initPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    initLocation()

                } else {
                    ToastUtil.showCenterToast(
                        this,
                        "您拒绝了如下权限：$deniedList",
                        ToastUtil.ToastType.WARN
                    )
                    finish()
                }
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        initPermission()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun getContext(): Context {
        return this
    }

    fun initLocation() {
        RetrofitClient.instance?.getApi()
            ?.GetLocation()
            ?.compose(RxScheduler.Flo_io_main())
            ?.`as`(bindAutoDispose())
            ?.subscribe(object : SubscriberLocation(this) {
                override fun onHandleSuccess(result: LocationBean?, message: String?) {
                    changeCities(result!!)
                }
            }
            )
    }

    //根据当前位置，将所在城市的天气放在首位
    fun changeCities(locationBean: LocationBean) {
        var inCities = false
        for (i in 0 until cities.size) {
            if (locationBean?.getAdcode().equals(cities[i])) {
                inCities = true
                val copyOf1 = cities.copyOfRange(0, i)
                val copyOf2 = cities.copyOfRange(i + 1, cities.size)
                var newcities = arrayOf(locationBean?.getAdcode())
                newcities = newcities.plus(copyOf1)
                newcities = newcities.plus(copyOf2)
                cities = arrayOf(newcities.joinToString())
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("cities", cities.joinToString())
                Log.d(TAG, cities.joinToString())
                startActivity(intent)
                finish()
                break
            }
        }
        if(!inCities){
            //若当前城市不在6个城市内，则添加城市到首位
            var newcities = arrayOf(locationBean?.getAdcode())
            newcities = newcities.plus(cities)
            cities = arrayOf(newcities.joinToString())
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("cities", cities.joinToString())
            Log.d(TAG, cities.joinToString())
            startActivity(intent)
            finish()
        }
    }

}