package com.hxz.basehttp

import com.hxz.basehttp.api.GankApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @title com.hxz.basehttp  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des RetrofitManager
 * @DATE 2020/5/29  11:24 星期五
 */
object RetrofitManager {

    private const val CONNECT_TIME = 10L
    private const val READ_TIME_OUT = 20L


    private val okHttp : OkHttpClient
    get() {
        return OkHttpClient.Builder()
            .initParam()
            .build()
    }

    var baseUrl: String = ""
    set(value) {
        if (value.isNotEmpty()) field = value
    }
    get() {
        return if (field.isNotEmpty()) field else BuildConfig.base_url
    }

    val mRetrofit : Retrofit
    get() {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api by lazy { createApi(GankApi::class.java) }

    fun<T> createApi(clz: Class<T>) : T = mRetrofit.create(clz)

    private fun OkHttpClient.Builder.initParam(): OkHttpClient.Builder {
        connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
        readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        return this
    }



}