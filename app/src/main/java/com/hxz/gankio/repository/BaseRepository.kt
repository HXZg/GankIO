package com.hxz.gankio.repository

import com.hxz.basehttp.RetrofitManager
import com.hxz.basehttp.bean.BaseResponseBean

open class BaseRepository {

    companion object {
        // all article girl gank
        const val CATEGORY_ALL = "All"
        const val CATEGORY_GANK = "GanHuo"
        const val CATEGORY_ARTICLE = "Article"
        const val CATEGORY_GIRL = "Girl"
    }

    val api by lazy { RetrofitManager.api }

    suspend fun<T> cacheException(action: suspend () -> BaseResponseBean<T>) : BaseResponseBean<T> {
        return try {
            action.invoke()
        }catch (e: Exception) {
            BaseResponseBean.customError(e)
        }
    }
}