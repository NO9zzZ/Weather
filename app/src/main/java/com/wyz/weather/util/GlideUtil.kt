package com.wyz.weather.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.wyz.weather.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.concurrent.ExecutionException


/**
 * @author zzZ
 * @date 2022/7/21 15:43
 * @describe
 * @e-mail:646888521@qq.com
 */
object GlideUtil {
    /**
     * @param context
     * @param url
     * @param imageView
     */
    fun loadItemBack(context: Context?, url: Int?, imageView: ImageView) {
        val options = RequestOptions.bitmapTransform(RoundedCorners(30))
        Glide.with(context!!).load(url)
            .apply(options)
            .into(imageView)
    }

    /**
     * 这是一个耗时的操作需要异步处理
     *
     * @param url 通过URL得到 Drawable
     * @return
     */
    fun getDrawableGlide(context: Context?, url: String?): Drawable? {
        try {
            return Glide.with(context!!)
                .load(url)
                .submit()
                .get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return null
    }

    fun loadDrawable(context: Context?, url: String?, imageView: ImageView) {
        Observable.create { emitter ->
            val drawable = getDrawableGlide(context, url)
            emitter.onNext(drawable!!)
            emitter.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ drawable -> imageView.background = drawable }
            ) { }
    }

    /**
     * @param context
     * @param resId
     * @param imageView
     */
    fun load(context: Context?, @DrawableRes resId: Int, imageView: ImageView) {
        Glide.with(context!!)
            .load(resId)
            .apply(getOptions())
            .into(imageView)
    }

    fun loadAsBitmap(context: Context?, url: String?, imageView: ImageView) {
        Glide.with(context!!)
            .asBitmap()
            .load(url)
            .into(imageView)
    }

    fun clear(context: Context?, imageView: ImageView) {
        Glide.with(context!!).clear(imageView)
    }

    fun load(context: Context?, url: String?, imageView: ImageView) {
        Glide.with(context!!)
            .load(url)
            .apply(getOptions())
            .into(imageView)
    }

    private fun getOptions(): RequestOptions {
        return RequestOptions()
//            .placeholder() //加载成功之前占位图
            .error(R.drawable.error) //加载错误之后的错误图
        //                .override(400,400)	//指定图片的尺寸
        //                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
        //                .fitCenter()
        //                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
        //                .centerCrop()
        //                .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
        //                .skipMemoryCache(true)	//跳过内存缓存
        //                .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
        //                .diskCacheStrategy(DiskCacheStrategy.NONE)	//跳过磁盘缓存
        //                .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
        //                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
    }

    private fun getOptionsNoCache(): RequestOptions {
        return RequestOptions()
            .skipMemoryCache(true) // 不使用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存;
    }

    interface OnGlideListener {
        fun onSuccess(file: File?)
    }
}
