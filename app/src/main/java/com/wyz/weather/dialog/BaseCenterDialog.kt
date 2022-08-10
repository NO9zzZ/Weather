package com.wyz.weather.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import com.wyz.weather.R

/**
 * @author zzZ
 * @date 2022/7/21 17:49
 * @describe 居中DIalog，修改p即可修改尺寸
 * @e-mail:646888521@qq.com
 */
open class BaseCenterDialog(context: Context?) :
    Dialog(context!!, R.style.MyDialogStyle) {
    override fun show() {
        // TODO Auto-generated method stub
        super.show()
        val dialogWindow = window
        dialogWindow!!.setGravity(Gravity.CENTER)
        val m = dialogWindow.windowManager
        val d = m.defaultDisplay
        val p = window!!.attributes
        p.dimAmount = 0.3f
        p.height = d.height
        p.width = d.width
        dialogWindow.attributes = p
    }
}
