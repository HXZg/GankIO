package com.hxz.baseui

import android.app.Application
import com.hxz.baseui.util.AppUtils
import com.hxz.baseui.util.CrashUtils

/**
 * @title com.hxz.baseui  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BaseApp
 * @DATE 2020/5/29  9:24 星期五
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppUtils.init(this)

        CrashUtils.init()
    }
}