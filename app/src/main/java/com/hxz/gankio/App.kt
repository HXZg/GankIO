package com.hxz.gankio

import com.hxz.baseui.BaseApp
import com.hxz.baseui.util.LogUtils

/**
 * @title com.hxz.gankio  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des App
 * @DATE 2020/5/29  16:20 星期五
 */

class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.getConfig().setLog2FileSwitch(true).isLogSwitch = true
    }
}