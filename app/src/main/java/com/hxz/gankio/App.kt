package com.hxz.gankio

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.hxz.basehttp.RetrofitManager
import com.hxz.basehttp.RetrofitParam
import com.hxz.baseui.BaseApp
import com.hxz.baseui.util.LogUtils
import com.hxz.gankio.utils.SPUtils
import okhttp3.Cache
import okhttp3.OkHttpClient

/**
 * @title com.hxz.gankio  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des App
 * @DATE 2020/5/29  16:20 星期五
 */

class App : BaseApp() {

    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        setAppMode()
        RetrofitManager.param = object : RetrofitParam() {
            override fun initOkHttpParam(builder: OkHttpClient.Builder) {
                super.initOkHttpParam(builder)
                builder.cache(Cache(cacheDir,10 * 1024 * 1024))
            }
        }
        context = this
//        LogUtils.getConfig().setLog2FileSwitch(true).isLogSwitch = true
    }

    private fun setAppMode() {
        AppCompatDelegate.setDefaultNightMode(SPUtils.getDarkMode(this))
    }
}