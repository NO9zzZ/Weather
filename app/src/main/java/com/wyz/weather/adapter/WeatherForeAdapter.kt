package com.wyz.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wyz.weather.bean.WeatherForeCastsBean
import com.wyz.weather.databinding.ItemForeBinding
import com.wyz.weather.util.GetDateFromNumUtil


/**
 * @author zzZ
 * @date 2022/7/21 16:07
 * @describe
 * @e-mail:646888521@qq.com
 */
class WeatherForeAdapter(list: MutableList<WeatherForeCastsBean>, context: Context) :
    RecyclerView.Adapter<WeatherForeAdapter.ViewHolder?>(), View.OnClickListener {
    private var mList: MutableList<WeatherForeCastsBean>
    private var mContext: Context? = null
    private var mRecyclerView: RecyclerView? = null

    class ViewHolder(itemBinding: ItemForeBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var mWeek: TextView
        var mTemp: TextView
        var mWeather: TextView
        var mWind: TextView

        init {
            mWeek = itemBinding.tvWeek
            mWeather = itemBinding.tvWeather
            mTemp = itemBinding.tvTemp
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
        val binding: ItemForeBinding = ItemForeBinding.inflate(
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

        if (position == 0)
            holder.mWeek.text = "今天"
        else
            holder.mWeek.text = GetDateFromNumUtil().getDay(mList.get(position).getWeek()!!.toInt())
        if (mList.get(position).getDayweather().equals(mList.get(position).getNightweather()))
            holder.mWeather.text = mList.get(position).getDayweather()
        else
            holder.mWeather.text =
                mList.get(position).getDayweather() + "转" + mList.get(position).getNightweather()

        var daywind =
            mList.get(position).getDaywind() + "风" + mList.get(position).getDaypower() + "级"
        var nightwind =
            mList.get(position).getNightwind() + "风" + mList.get(position).getNightpower() + "级"

        if (daywind.equals(nightwind))
            holder.mWind.text = daywind
        else
            holder.mWind.text = nightwind + "  转  " + nightwind

        var daytemp = mList.get(position).getDaytemp()
        var nighttemp = mList.get(position).getNighttemp()
        if (daytemp.equals(nighttemp))
            holder.mTemp.text = nighttemp + "°"
        else {
            if (daytemp!!.toInt() > nighttemp!!.toInt())
                holder.mTemp.text =
                    nighttemp + "°—" + daytemp + "°"
            else
                holder.mTemp.text =
                    daytemp + "°—" + nighttemp + "°"

        }
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
