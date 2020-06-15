package com.hxz.gankio

import androidx.appcompat.app.AppCompatDelegate
import com.hxz.baseui.BaseApp
import com.hxz.baseui.util.LogUtils
import com.hxz.gankio.utils.SPUtils

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
        setAppMode()
//        LogUtils.getConfig().setLog2FileSwitch(true).isLogSwitch = true
    }

    private fun setAppMode() {
        AppCompatDelegate.setDefaultNightMode(SPUtils.getDarkMode(this))
    }
}