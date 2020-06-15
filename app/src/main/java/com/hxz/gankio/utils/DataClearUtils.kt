package com.hxz.gankio.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.math.BigDecimal

/**
 * @title com.hxz.gankio.utils  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des DataClearUtils
 * @DATE 2020/6/15  15:08 星期一
 */
object DataClearUtils {

    fun getTotalCacheSize(context: Context) : String{
        var size = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val file = context.externalCacheDir
            if (file != null) size += getFolderSize(file)
        }
        return getFormatSize(size)
    }

    fun getFolderSize(file: File) : Long {
        var size = 0L
        if (file.isDirectory) {
            file.listFiles()?.forEach {
                size += getFolderSize(it)
            }
        } else {
            size += file.length()
        }
        return size
    }

    fun clearAllCache(context: Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val file = context.externalCacheDir
            if (file != null) deleteDir(file)
        }
    }

    private fun deleteDir(dir: File) : Boolean {
        if (dir.isDirectory) {
            dir.list()?.forEach {
                val success = deleteDir(File(dir,it))
                if (!success) {
                    return false
                }
            }
        }
        return dir.delete()
    }

    fun getFormatSize(size: Long) : String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return "0K"
        }

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(megaByte)
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "K"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(megaByte)
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "M"
        }

        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(gigaByte)
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }

}