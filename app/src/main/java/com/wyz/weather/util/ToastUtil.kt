package com.wyz.weather.util

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.wyz.weather.R

/**
 * @author zzZ
 * @date 2022/7/21 11:18
 * @describe
 * @e-mail:646888521@qq.com
 */
object ToastUtil {
    private var toast //实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长
            : Toast? = null

    /**
     * 短时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    fun showShortToast(context: Context?, msg: String?) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        if (context != null) {
//            if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            //            } else {
//                toast.setText(msg);
//            }
            //1、setGravity方法必须放到这里，否则会出现toast始终按照第一次显示的位置进行显示（比如第一次是在底部显示，那么即使设置setGravity在中间，也不管用）
            //2、虽然默认是在底部显示，但是，因为这个工具类实现了中间显示，所以需要还原，还原方式如下：
//            toast.setGravity(Gravity.BOTTOM, 0, dip2px(MyApplication.getAppContext(), 64));
            toast?.show()
        }
    }

    /**
     * 长时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    fun showLongToast(context: Context?, msg: String?) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        if (context != null) {
                toast?.cancel()
                toast = null
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
                toast!!.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast!!.show()
            }
        }
    }

    /**
     * 居中
     *
     * @param msg 显示的内容-字符串
     */
    fun showCenterToast(context: Context?, msg: String?, type: ToastType) {
        if (context != null) {
            if (toast != null) {
                toast!!.cancel()
                toast = null
            }
            if (toast == null) {
                toast = Toast(context)
                val toastView = View.inflate(context, R.layout.toast, null)
                toast!!.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast!!.duration = Toast.LENGTH_LONG
                toast!!.setView(toastView)
                val tv_message = toastView.findViewById<View>(R.id.tv_message) as TextView
                tv_message.text = msg
                val iv_icon = toastView.findViewById<View>(R.id.iv_icon) as ImageView
                iv_icon.setImageResource(if (type == ToastType.SUCCESS) R.drawable.duigou else if (type == ToastType.ERROR) R.drawable.error else R.drawable.warn)
                toast!!.show()
            }
        }
    }

    enum class ToastType {
        SUCCESS, ERROR, WARN
    }
}
