package com.wyz.weather.flownet

import com.wyz.weather.flownet.baseretrofit.BaseRetrofitClient
import okhttp3.OkHttpClient

/**
 * @author zzZ
 * @date 2022/8/10 13:02
 * @describe
 * @e-mail:ywang450@volvocars.com
 */
object MyRetrofitClient : BaseRetrofitClient() {
    val service by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) = Unit

}