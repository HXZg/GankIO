package com.hxz.gankio.repository

import androidx.lifecycle.liveData
import com.bumptech.glide.disklrucache.DiskLruCache
import com.hxz.basehttp.RetrofitManager
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.baseui.util.LogUtils
import com.hxz.gankio.App
import kotlinx.coroutines.Dispatchers

open class BaseRepository {

    companion object {
        // all article girl gank
        const val CATEGORY_ALL = "All"
        const val CATEGORY_GANK = "GanHuo"
        const val CATEGORY_ARTICLE = "Article"
        const val CATEGORY_GIRL = "Girl"

        const val PAGE_COUNT = 10

        const val HOT_PAGE_COUNT = 20  // 热门数据一次加载完
    }

    val diskCache by lazy {
        DiskLruCache.open(App.context.getExternalFilesDir("home_disk"),1,1,10 * 1024 * 1024L)
    }

    val api by lazy { RetrofitManager.api }

    fun<T> fire(block: suspend () -> BaseResponseBean<T>) = liveData<BaseResponseBean<T>>(Dispatchers.IO) {
        val result = try {
            block()
        }catch (e: Exception) {
            BaseResponseBean.customError<T>(e)
        }
        emit(result)
    }

    fun putDiskCache(key: String,value: String) {
        val edit = diskCache.edit(key)
        edit.set(0,value)
        edit.commit()
        diskCache.flush()  // 同步操作记录
    }

    fun getDiskCache(key: String) : String? {
        val value = diskCache.get(key) ?: return null
        return value.getString(0)
    }
}