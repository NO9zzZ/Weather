package com.wyz.weather

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wyz.weather.adapter.WeatherAdapter
import com.wyz.weather.bean.WeatherBean
import com.wyz.weather.bean.WeatherForeBean
import com.wyz.weather.databinding.ActivityMainBinding
import com.wyz.weather.dialog.ForePopUpWindow
import com.wyz.weather.retrofit.Subscriber
import com.wyz.weather.retrofit.RetrofitClient
import com.wyz.weather.retrofit.RxScheduler
import com.wyz.weather.retrofit.SubscriberFore

class MainActivity : BaseActivity() {
    lateinit var mBinding: ActivityMainBinding

    var weatherBeanList: MutableList<WeatherBean>? = mutableListOf()
    var weatherAdapter: WeatherAdapter? = null
    var sum: Int? = 0
    lateinit var cities: Array<String>


    override fun loadData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        weatherAdapter = WeatherAdapter(weatherBeanList!!, this)
        val layoutManager = LinearLayoutManager(this)
        mBinding.rv.layoutManager = layoutManager
        mBinding.rv.adapter = weatherAdapter
        weatherAdapter!!.setOnItemClickListener(object : WeatherAdapter.OnItemClickListener {
            override fun onItemClick(parent: RecyclerView?, view: View?, position: Int) {
                var weatherBean = weatherBeanList?.get(position)
                //获取city预报信息
                RetrofitClient.instance?.getApi()
                    ?.GetWeatherFore(weatherBean?.getAdcode(), "all")
                    ?.compose(RxScheduler.Flo_io_main())
                    ?.`as`(bindAutoDispose())
                    ?.subscribe(object : SubscriberFore<MutableList<WeatherForeBean?>>(this@MainActivity) {
                        override fun onHandleSuccess(result: MutableList<WeatherForeBean?>?, message: String?) {
                            var bagPopUpWindow: ForePopUpWindow? =
                                ForePopUpWindow(this@MainActivity, result!!.get(0)!!, weatherBean!!)
                            bagPopUpWindow?.show()
                        }
                    }
                    )
            }
        })
        var s = getIntent().getExtras()!!.getString("cities")!!
        cities = s.replace("\\s".toRegex(), "").split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
       //获取天气信息，可用于刷新逻辑
        weatherBeanList?.clear()
        for (i in 0 until cities.size) {
            initWeather(cities[i])
        }
    }

    //获取city天气信息
    fun initWeather(city: String) {

        RetrofitClient.instance?.getApi()
            ?.GetWeather(city)
            ?.compose(RxScheduler.Flo_io_main())
            ?.`as`(bindAutoDispose())
            ?.subscribe(object : Subscriber<MutableList<WeatherBean>>(this) {
                override fun onHandleSuccess(result: MutableList<WeatherBean>?, message: String?) {
                    if (result?.size!! > 0) {
                        weatherBeanList?.add(result?.get(0)!!)
                        sum = sum!! + 1
                        if (sum == cities.size) sortList()
                        else weatherAdapter?.notifyDataSetChanged()
                    }
                }
            }
            )
    }

    //根据cities排序weatherBeanList
    fun sortList() {
        var list = sort(weatherBeanList!!)
        weatherBeanList?.clear()
        weatherBeanList?.addAll(list)
        weatherAdapter?.notifyDataSetChanged()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun getContext(): Context {
        return this
    }

    fun sort(list: MutableList<WeatherBean>): MutableList<WeatherBean> {
        var map: MutableMap<String, WeatherBean> = mutableMapOf()
        var sortList: MutableList<WeatherBean> = mutableListOf()
        for (i in 0 until list.size) {
            map.put(list[i].getAdcode().toString(), list[i])
        }
        for (j in 0 until cities.size) {
//            Log.d(TAG,map.get(cities[j])!!.getCity().toString())
            sortList?.add(map.get(cities[j])!!)
        }
        return sortList
    }

}

