package com.wyz.weather.kotlin

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

/**
 * @author zzZ
 * @date 2022/7/22 10:06
 * @describe
 * @e-mail:646888521@qq.com
 */
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLog::class], sdk = [23], application = Application::class)
abstract class BaseTestRobolectricClass {

    protected val mContext: Context = ApplicationProvider.getApplicationContext()

    companion object {
        @JvmStatic
        protected val TAG: String = this::class.java.simpleName

        @BeforeClass
        @JvmStatic
        fun setup() {
            ShadowLog.stream = System.out
        }
    }

}

