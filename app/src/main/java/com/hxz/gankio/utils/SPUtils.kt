package com.hxz.gankio.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

/**
 * @title com.hxz.gankio.utils  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des SPUtils
 * @DATE 2020/6/15  14:18 星期一
 */
object SPUtils {

    private const val APP_SP = "sp_set"
    private const val APP_DARK_SET = "set_dark"

    private fun getSp(context: Context) = context.getSharedPreferences(APP_SP,Context.MODE_PRIVATE)

    fun setDarkMode(context: Context,mode: Int) {
        getSp(context).edit {
            putInt(APP_DARK_SET,mode)
        }
    }

    fun getDarkMode(context: Context) = getSp(context).getInt(APP_DARK_SET,AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)


}