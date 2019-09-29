package com.ct.aacgank.app

import android.app.Application
import com.ct.aacgank.common.util.CLog

/**
 * 文件名 GankApplication
 * 创建者  CT
 * 时 间  2019/9/29 10:46
 * TODO
 */
class GankApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CLog.Builder(this)
            .setGlobalTag("Gank")


    }
}