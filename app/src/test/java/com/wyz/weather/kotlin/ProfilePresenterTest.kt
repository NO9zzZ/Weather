package com.wyz.weather.kotlin

import android.app.Application
import com.wyz.weather.util.GetDateFromNumUtil
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

/**
 * @author zzZ
 * @date 2022/7/22 10:02
 * @describe
 * @e-mail:646888521@qq.com
 */
@Config(shadows = [ShadowLog::class], sdk = [23], application = Application::class)
class ProfilePresenterTest : BaseTestRobolectricClass() {

    @Spy
    var v: Int = 3

    @Mock
    lateinit var p: GetDateFromNumUtil

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        p = spy(GetDateFromNumUtil::class.java)
        p.getDay(v)
    }

    /**
     * 命名规则：期望输出_测试场景
     */
    @Test
    fun fiveMethodsShouldBeInvoked_WhenInitData() {
        p.getDay(v)
        val mockList = Mockito.mock(mutableListOf<String>().javaClass)
        verify(v, atLeastOnce()).equals("周三")
//        verify(v).updateWeight("")
//        verify(v, never()).finishActivity()
//        verify(v, atLeastOnce()).updateHeight("")
//        verify(v, atLeast(1)).updateExerciseGoal("")
//        verify(v, times(1)).updateExerciseFrequency("")
//        verify(v, atMost(1)).updateExerciseTime("")
        // 检查是否所有的用例都涵盖了,如果没有将测试失败。放在所有的测试后面
        verifyNoMoreInteractions(v)
    }



    @Test
    fun finishActivityMethodShouldBeInvoked_WhenResetUserInfo() {
    }

    companion object {
        private val TAG = ProfilePresenterTest::class.java.simpleName
    }

}

