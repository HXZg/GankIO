package com.hxz.gankio.utils

import android.content.Context
import android.util.TypedValue

fun Int.dp2px(context: Context) : Int  =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics).toInt()

fun Int.sp2px(context: Context) : Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,this.toFloat(),context.resources.displayMetrics).toInt()

