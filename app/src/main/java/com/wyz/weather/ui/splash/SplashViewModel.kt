package com.wyz.weather.ui.splash

import android.Manifest
import android.app.Application
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import com.permissionx.guolindev.PermissionX
import com.wyz.weather.MyApplication
import com.wyz.weather.bean.LocationBean
import com.wyz.weather.retrofit.RetrofitClient
import com.wyz.weather.retrofit.RxScheduler
import com.wyz.weather.retrofit.SubscriberLocation
import com.wyz.weather.view.BaseView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


/**
 * @author zzZ
 * @date 2022/8/9 20:58
 * @describe
 * @e-mail:646888521@qq.com
 */
class SplashViewModel(application: Application) : AndroidViewModel(application),
    KoinComponent {
    private val mModel: SplashModel by inject()

    fun getCities() = mModel.cities()
    fun getCodePms() = mModel.codepms()

    fun initLocation(view: BaseView) {
        RetrofitClient.instance?.getApi()
            ?.GetLocation()
            ?.compose(RxScheduler.Flo_io_main())
//            ?.`as`(
//                AutoDispose.autoDisposable(
//                AndroidLifecycleScopeProvider
//                    .from(context, Lifecycle.Event.ON_DESTROY)
//            ))
            ?.subscribe(object : SubscriberLocation(view) {
                override fun onHandleSuccess(result: LocationBean?, message: String?) {
                    changeCities(result!!)
                }
            }
            )
    }

    //根据当前位置，将所在城市的天气放在首位
    fun changeCities(locationBean: LocationBean) {
        var code = -1;
        var inCities = false
        var cities = getCities()
        for (i in 0 until cities.size) {
            if (locationBean?.getAdcode().equals(cities[i])) {
                inCities = true
                val copyOf1 = cities.copyOfRange(0, i)
                val copyOf2 = cities.copyOfRange(i + 1, cities.size)
                var newcities = arrayOf(locationBean?.getAdcode())
                newcities = newcities.plus(copyOf1)
                newcities = newcities.plus(copyOf2)
                cities = arrayOf(newcities.joinToString())
                mModel.setCity(cities)
                mModel.setCodePms(0)
                break
            }
        }
        if (!inCities) {
            //若当前城市不在6个城市内，则添加城市到首位
            var newcities = arrayOf(locationBean?.getAdcode())
            newcities = newcities.plus(cities)
            cities = arrayOf(newcities.joinToString())
            mModel.setCity(cities)
            mModel.setCodePms(0)
        }
    }
}
