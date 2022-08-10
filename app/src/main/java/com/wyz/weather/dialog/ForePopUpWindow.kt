package com.wyz.weather.dialog

import android.app.Activity
import android.content.DialogInterface
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.wyz.weather.R
import com.wyz.weather.adapter.WeatherForeAdapter
import com.wyz.weather.bean.WeatherBean
import com.wyz.weather.bean.WeatherForeBean
import com.wyz.weather.bean.WeatherForeCastsBean
import com.wyz.weather.databinding.PopBagBinding
import com.wyz.weather.util.GlideUtil

/**
 * @author zzZ
 * @date 2022/7/21 17:44
 * @describe
 * @e-mail:646888521@qq.com
 */
class ForePopUpWindow(context: Activity,weatherForeBean: WeatherForeBean,mainInfo: WeatherBean) : BaseCenterDialog(context) {
    lateinit var mBinding: PopBagBinding
    private val context: Activity
    private var mWeatherForeAdapter: WeatherForeAdapter? = null
    private var result: MutableList<WeatherForeCastsBean> = mutableListOf()
    private fun initRecyclerView() {
        //纵向线性布局
        mWeatherForeAdapter = WeatherForeAdapter(result!!, context)
        val layoutManager = LinearLayoutManager(context)
        mBinding.rv.layoutManager = layoutManager
        mBinding.rv.adapter = mWeatherForeAdapter
        mBinding.llBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dismiss()
            }
        })
    }

    private fun initView(weatherForeBean: WeatherForeBean,mainInfo: WeatherBean) {
        result.clear()
        result.addAll(weatherForeBean.getCasts()!!)
        mWeatherForeAdapter?.notifyDataSetChanged()


        mBinding.tvCity.text = mainInfo.getCity()
        var today = weatherForeBean.getCasts()?.get(0)
        mBinding.tvHighlow.text = "最低"+ today?.getDaytemp()+"°" + "最高"+ today?.getNighttemp()+"°"
        mBinding.tvWeather.text = mainInfo.getWeather()
        mBinding.tvTemp.text = mainInfo.getTemperature()+"°"

    }


    init {
        this.context = context
        mBinding = PopBagBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.ivBg.setImageResource(R.drawable.sunnybg)
        if(mainInfo.getWeather()?.contains("阴")!!){
            mBinding.ivBg.setImageResource(R.drawable.cloudybg)
        }else if(mainInfo.getWeather()?.contains("云")!!){
            mBinding.ivBg.setImageResource(R.drawable.cloudbg)
        }else if(mainInfo.getWeather()?.contains("雾")!!){
            mBinding.ivBg.setImageResource(R.drawable.smogbg)
        }else if(mainInfo.getWeather()?.contains("雨")!!){
            GlideUtil.loadItemBack(context, R.drawable.rainbg,mBinding.ivBg)
        }else if(mainInfo.getWeather()?.contains("雪")!!){
            GlideUtil.loadItemBack(context, R.drawable.snowbg,mBinding.ivBg)
        }
        initRecyclerView()
        initView(weatherForeBean,mainInfo)
    }
}
