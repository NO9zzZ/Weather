package com.wyz.weather

import android.app.Application
import com.wyz.weather.ui.splash.SplashModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * @author zzZ
 * @date 2022/8/9 17:10
 * @describe
 * @e-mail:646888521@qq.com
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            //给Koin框架添加ApplicationContext
            androidContext(this@MyApplication)
            /*
                这里设置Koin的日志打印
                Koin提供了三种实现:
                AndroidLogger:使用Android的Log.e/i/d()打印日志
                PrintLogger:使用System.err/out打印日志
                EmptyLogger:不打印日志，默认就是该实现
             */
            logger(AndroidLogger())
            /*
                设置Koin配置文件，需要放在assets文件夹中
                默认名称为：koin.propreties
                可以快速获取配置文件中的内容，文件名可以修改，但是需要在这里保持一致
                [getKoin().getProperty<String>("name")]
             */
            androidFileProperties("koin.properties")
            modules(
                /*
                    添加Module对象
                 */
                module {
                    /*
                        实例工厂，每次获取都是新的实例对象
                     */
//                    factory { FactoryModel() }
                    single { SplashModel() }

                }
            )
        }
    }
}

