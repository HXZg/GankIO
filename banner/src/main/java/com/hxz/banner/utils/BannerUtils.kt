package com.hxz.banner.utils

/**
 * @title com.hxz.banner.utils  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BannerUtils
 * @DATE 2020/5/30  14:50 星期六
 */

typealias PageClickListener = (position: Int) -> Unit

internal object BannerUtils {

    const val MAX_VALUE_SIZE = 500

    var isCanLoop = false

    var isAutoPlay = false

    var clickListener: PageClickListener? = null

    fun getResetItem(item: Int,pageSize: Int) : Int {  // 轮播从中间部分开始
        return if (!isCanLoop) item
        else MAX_VALUE_SIZE / 2 - MAX_VALUE_SIZE / 2 % pageSize + item
    }
}