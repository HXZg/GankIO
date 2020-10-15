package com.hxz.basehttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @title com.hxz.basehttp  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des LookIntercept
 * @DATE 2020/10/15  15:33 星期四
 */
class LookIntercept : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        for (i in 0 until response.headers().size()) {
            Log.i("zzzzzzzzzzzz",response.headers().name(i) + "::" + response.headers().value(i))
        }
        return response
    }
}