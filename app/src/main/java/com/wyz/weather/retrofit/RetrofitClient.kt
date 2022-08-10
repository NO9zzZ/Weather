package com.wyz.weather.retrofit

import com.wyz.weather.BuildConfig
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author zzZ
 * @date 2022/7/21 13:15
 * @describe
 * @e-mail:646888521@qq.com
 */
class RetrofitClient private constructor() {
    private var apiService: APIService? = null
    var baseUrl = "https://restapi.amap.com/v3/"

    /**
     * 设置Header
     *
     * @return
     */
    private fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
//            Request original = chain.request();
            val originalRequest = chain.request()
            var request: Request? = null
            val requestBuilder = originalRequest.newBuilder() //添加Token

            val method = originalRequest.method() //获取请求方法
            if (method == "GET") { //添加参数
                val builder = originalRequest.url().newBuilder()
                builder.addQueryParameter("key", BuildConfig.AMapKEY)
                request = requestBuilder.url(builder.build()).build() //构建request
                return@Interceptor chain.proceed(request)
            } else if (method == "POST") {
                if (originalRequest.body() is FormBody) {
                    val bodyBuilder = FormBody.Builder()
                    var formBody = originalRequest.body() as FormBody?
                    //遍历boby
                    var i = 0
                    while (i < formBody!!.size()) {

                        bodyBuilder.addEncoded(
                            formBody!!.encodedName(i),
                            formBody!!.encodedValue(i)
                        )
                        i++
                    }
                    formBody = bodyBuilder.addEncoded("requestFrom", "1").build()
                    request = requestBuilder.post(formBody).build()
                    return@Interceptor chain.proceed(request)
                }
            }
            chain.proceed(originalRequest)
        }

    }

    /**
     * 设置拦截器
     *
     * @return
     */
    private fun getInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        //显示日志
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        //        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor
    }
    fun getApi(): APIService? {
        //初始化一个client,不然retrofit会自己默认添加一个
        val client = OkHttpClient().newBuilder() //设置Header
            .addInterceptor(getHeaderInterceptor()) //设置拦截器
            .addInterceptor(getInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .client(client) //设置网络请求的Url地址
            .baseUrl(baseUrl) //设置数据解析器
            .addConverterFactory(GsonConverterFactory.create()) //设置网络请求适配器，使其支持RxJava与RxAndroid
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        //创建—— 网络请求接口—— 实例
        apiService = retrofit.create(APIService::class.java)
        return apiService
    }

    companion object {
        @Volatile
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    synchronized(RetrofitClient::class.java) {
                        if (field == null) {
                            field = RetrofitClient()
                        }
                    }
                }
                return field
            }
    }
}
