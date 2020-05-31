package com.hxz.banner.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import androidx.annotation.DrawableRes

/**
 * @title com.hxz.banner.utils  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BannerUtils
 * @DATE 2020/5/30  14:50 星期六
 */

typealias PageClickListener = (position: Int) -> Unit

internal object BannerUtils {

    private const val DEFAULT_POINT_SIZE = 20

    const val MAX_VALUE_SIZE = 500

    var isCanLoop = false

    var isAutoPlay = false

    var clickListener: PageClickListener? = null

    fun getResetItem(item: Int,pageSize: Int) : Int {  // 轮播从中间部分开始
        return if (!isCanLoop || pageSize <=  0) item
        else MAX_VALUE_SIZE / 2 - MAX_VALUE_SIZE / 2 % pageSize + item
    }

    fun createStateListDrawable(unSelect: Drawable,select: Drawable) : Drawable {
        val listDrawable = StateListDrawable()
        listDrawable.addState(intArrayOf(android.R.attr.state_selected),select)
        listDrawable.addState(intArrayOf(-android.R.attr.state_selected),unSelect)
        return listDrawable
    }

    fun createColorListDrawable(unSelect: Int,select: Int) : Drawable = createStateListDrawable(
        createDrawable(unSelect), createDrawable(select))

    private fun createDrawable(color: Int) : Drawable{
        val drawable = GradientDrawable()
        drawable.setColor(color)
        drawable.setBounds(0,0, DEFAULT_POINT_SIZE, DEFAULT_POINT_SIZE)
        drawable.shape = GradientDrawable.OVAL
        drawable.cornerRadius = (DEFAULT_POINT_SIZE / 2).toFloat()
        drawable.setSize(DEFAULT_POINT_SIZE,DEFAULT_POINT_SIZE)
        return drawable
    }
}