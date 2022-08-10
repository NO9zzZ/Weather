package com.wyz.weather.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wyz.weather.BaseActivity
import com.wyz.weather.adapter.WeatherAdapter
import com.wyz.weather.bean.WeatherBean
import com.wyz.weather.databinding.ActivityMainBinding
import com.wyz.weather.dialog.ForePopUpWindow
import com.wyz.weather.util.launchAndCollect

class MainActivity : BaseActivity() {
    lateinit var mBinding: ActivityMainBinding
    private val mViewModel by viewModels<MainViewModel>()

    var weatherAdapter: WeatherAdapter? = null
    var sum: Int? = 0


    override fun loadData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        weatherAdapter = WeatherAdapter(mViewModel.getList()!!, this)
        val layoutManager = LinearLayoutManager(this)
        mBinding.rv.layoutManager = layoutManager
        mBinding.rv.adapter = weatherAdapter
        weatherAdapter!!.setOnItemClickListener(object : WeatherAdapter.OnItemClickListener {
            override fun onItemClick(parent: RecyclerView?, view: View?, position: Int) {
                var weatherBean = mViewModel.getList()?.get(position)
                if (weatherBean != null) {
                    //获取city预报信息
                    getWeatherFore(weatherBean?.getAdcode().toString(), weatherBean)
                }
            }
        })
        var s = getIntent().getExtras()!!.getString("cities")!!
        mViewModel.setCities(
            s.replace("\\s".toRegex(), "").split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
        //获取天气信息，可用于刷新逻辑
        mViewModel.getList()?.clear()
        for (i in 0 until mViewModel.getCities().size) {
            getWeather(mViewModel.getCities()[i])
//            initWeather(cities[i])
        }
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun getContext(): Context {
        return this
    }


    private fun getWeatherFore(city: String, weatherBean: WeatherBean) {
        launchAndCollect({
            mViewModel.getWeatherFore(city)
        }) {
            onSuccess = {
                var bagPopUpWindow: ForePopUpWindow? =
                    ForePopUpWindow(this@MainActivity, it?.get(0)!!, weatherBean!!)
                bagPopUpWindow?.show()
            }
            onFailed = { errorCode, errorMsg ->
                toast("errorCode: $errorCode   errorMsg: $errorMsg")
            }
        }
    }

    private fun getWeather(city: String) {
        launchAndCollect({
            mViewModel.getWeather(city)
        }) {
            onSuccess = {
                mViewModel.getList()?.add(it?.get(0)!!)
                sum = sum!! + 1
                if (sum == mViewModel.getCities().size) {
                    mViewModel.sortList()
                    weatherAdapter?.notifyDataSetChanged()
                }
            }
            onFailed = { errorCode, errorMsg ->
                toast("errorCode: $errorCode   errorMsg: $errorMsg")
            }
        }
    }

}

