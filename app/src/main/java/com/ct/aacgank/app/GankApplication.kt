package com.ct.aacgank.app

import android.app.Activity
import android.app.Application
import com.ct.aacgank.common.util.CLog
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * 文件名 GankApplication
 * 创建者  CT
 * 时 间  2019/9/29 10:46
 * TODO  2019/10/14 添加Dagger注入
 */
class GankApplication : Application(), HasActivityInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        //在这里初始化Component


        CLog.Builder(this)
            .setGlobalTag("Gank")

    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

}