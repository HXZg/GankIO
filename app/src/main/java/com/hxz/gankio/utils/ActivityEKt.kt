package com.hxz.gankio.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * @title com.hxz.gankio.utils  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des ActivityEKt
 * @DATE 2020/6/16  9:14 星期二
 */

inline fun<reified T: Activity> Context.startTActivity(crossinline action: Intent.() -> Unit = {}) {
    val i = Intent(this,T::class.java)
    i.action()
    startActivity(i)
}