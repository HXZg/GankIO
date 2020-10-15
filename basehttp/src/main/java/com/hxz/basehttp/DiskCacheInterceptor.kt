package com.hxz.basehttp

import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.time.measureTime

/**
 * @title com.hxz.basehttp  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des DiskCacheInterceptor
 * @DATE 2020/10/15  14:25 星期四
 */
class DiskCacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()
        val response = chain.proceed(newRequest)

        Log.i("zzzzzzzzzzzz","add cache control")
        /*return response.newBuilder()
            .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
            .removeHeader("Cache-Control")
            .header("Cache-Control", "public ,max-age=" + (12 * 60))
            .build()*/

        return response
    }
}