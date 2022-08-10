package com.wyz.weather.ui.splash

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.permissionx.guolindev.PermissionX
import com.wyz.weather.BaseActivity
import com.wyz.weather.R
import com.wyz.weather.databinding.ActivitySplashBinding
import com.wyz.weather.ui.main.MainActivity
import com.wyz.weather.util.ToastUtil
import com.wyz.weather.view.BaseView


/**
 * @author zzZ
 * @date 2022/7/21 11:03
 * @describe 启动页
 * @e-mail:646888521@qq.com
 */
class SplashActivity : BaseActivity() {
    //    private val mViewModel: SplashViewModel by viewModel()
    lateinit var mBinding: ActivitySplashBinding
    private val mViewModel: SplashViewModel by lazy { SplashViewModel(application) }


    override fun initView() {
        immersionStatusBar()
    }

    override fun loadData() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val cpView = findViewById<ComposeView>(R.id.cpView)
        cpView.setContent { SplashCpView() }

        initPermission(this);

    }


    @Preview
    @Composable
    private fun SplashCpView() {
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(250.dp))
            Text(
                text = stringResource(R.string.title),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(androidx.appcompat.R.dimen.abc_action_bar_content_inset_material))
                    .wrapContentWidth(Alignment.CenterHorizontally),
                fontSize = 40.sp
            )
            Spacer(Modifier.height(20.dp))
            Image(
                painter = painterResource(R.drawable.ic_launcher_round),
                contentDescription = "Splash",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(80.dp)
            )
        }

    }

    fun initPermission(view: BaseView) {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    mViewModel.initLocation(this)
                    Handler().postDelayed({
                        if (mViewModel.getCodePms() == 0) {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("cities", mViewModel.getCities().joinToString())
                            start(intent)
                            finish()
                        } else if (mViewModel.getCodePms() == -1) {
                            ToastUtil.showCenterToast(
                                this,
                                "发生异常！",
                                ToastUtil.ToastType.WARN
                            )
                            finish()
                        } else if (mViewModel.getCodePms() == -2) {

                        }
                    }, 1000)
                } else {
                    ToastUtil.showCenterToast(
                        this,
                        "您拒绝了必要权限！",
                        ToastUtil.ToastType.WARN
                    )
                    finish()
                }
            }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun getContext(): Context {
        return this
    }

}