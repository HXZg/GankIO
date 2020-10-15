package com.hxz.basehttp

import com.hxz.basehttp.api.GankApi
import okhttp3.Cache
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

    var param: RetrofitParam = RetrofitParam()

    private val okHttp : OkHttpClient
    get() {
        return OkHttpClient.Builder()
            .apply { param.initOkHttpParam(this) }
            .build()
    }

    val mRetrofit : Retrofit
    get() {
        return Retrofit.Builder()
            .client(okHttp)
            .apply { param.initRetrofitParam(this) }
            .build()
    }

    val api by lazy { createApi(GankApi::class.java) }

    fun<T> createApi(clz: Class<T>) : T = mRetrofit.create(clz)



}