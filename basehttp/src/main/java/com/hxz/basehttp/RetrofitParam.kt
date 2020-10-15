package com.hxz.basehttp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @title com.hxz.basehttp  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des RetrofitParam
 * @DATE 2020/10/15  14:40 星期四
 */
open class RetrofitParam {

    private val CONNECT_TIME = 10L
    private val READ_TIME_OUT = 20L

    open fun getBaseUrl() : String = BuildConfig.base_url

    open fun initOkHttpParam(builder: OkHttpClient.Builder) {
        builder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(LookIntercept())
            .addNetworkInterceptor(DiskCacheInterceptor())
    }

    open fun initRetrofitParam(builder: Retrofit.Builder) {
        builder.baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
    }
}