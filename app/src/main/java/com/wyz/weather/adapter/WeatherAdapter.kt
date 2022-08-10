package com.wyz.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wyz.weather.R
import com.wyz.weather.bean.WeatherBean
import com.wyz.weather.databinding.ItemInfoBinding
import com.wyz.weather.util.GlideUtil


/**
 * @author zzZ
 * @date 2022/7/21 16:07
 * @describe
 * @e-mail:646888521@qq.com
 */
class WeatherAdapter(list: MutableList<WeatherBean>,context: Context) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder?>(), View.OnClickListener {
    private var mList: MutableList<WeatherBean>
    private var mContext: Context?= null
    private var mRecyclerView: RecyclerView? = null

    class ViewHolder(itemBinding: ItemInfoBinding ) : RecyclerView.ViewHolder(itemBinding.root) {
        var mBg: ImageView
        var mCity: TextView
        var mTime: TextView
        var mTemp: TextView
        var mWeather: TextView
        var mWind: TextView

        init {
            mBg = itemBinding.ivBg
            mCity = itemBinding.tvCity
            mTime = itemBinding.tvTime
            mTemp = itemBinding.tvTemp
            mWeather = itemBinding.tvWeather
            mWind = itemBinding.tvWind
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView!!)
        this.mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView!!)
        this.mRecyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemInfoBinding = ItemInfoBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        )
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(parent: RecyclerView?, view: View?, position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        GlideUtil.loadItemBack(mContext,R.drawable.sunny,holder.mBg)
        if(mList.get(position).getWeather()?.contains("阴")!!){
            GlideUtil.loadItemBack(mContext,R.drawable.cloudy,holder.mBg)
        }else if(mList.get(position).getWeather()?.contains("云")!!){
            GlideUtil.loadItemBack(mContext, R.drawable.cloud,holder.mBg)
        }else if(mList.get(position).getWeather()?.contains("霾")!!){
            GlideUtil.loadItemBack(mContext, R.drawable.smog,holder.mBg)
        }else if(mList.get(position).getWeather()?.contains("雨")!!){
            GlideUtil.loadItemBack(mContext, R.drawable.rain,holder.mBg)
        }else if(mList.get(position).getWeather()?.contains("雪")!!){
            GlideUtil.loadItemBack(mContext, R.drawable.snow,holder.mBg)
        }


        holder.mCity.text = mList.get(position).getCity()
        holder.mWeather.text = mList.get(position).getWeather()
        var s = mList.get(position).getReporttime()
        holder.mTime.text = s?.substring(s.length-8,s.length-3)
        holder.mTemp.text = mList.get(position).getTemperature()+"°"
        holder.mWind.text = mList.get(position).getWinddirection()+"风"+mList.get(position).getWindpower()+"级"
    }

    override fun getItemCount(): Int {
        return mList.size
    }



    override fun onClick(view: View?) {
        val position = mRecyclerView!!.getChildAdapterPosition(view!!)
        if (onItemClickListener != null) {
            onItemClickListener!!.onItemClick(mRecyclerView, view, position)
        }
    }

    init {
        mList = list
        mContext = context
    }
}
