package com.hxz.gankio.repository

import com.hxz.basehttp.RetrofitManager
import com.hxz.basehttp.bean.BaseResponseBean

open class BaseRepository {

    val api by lazy { RetrofitManager.api }

    suspend fun<T> cacheException(action: suspend () -> BaseResponseBean<T>) : BaseResponseBean<T> {
        return try {
            action.invoke()
        }catch (e: Exception) {
            BaseResponseBean.customError(e)
        }
    }
}