package com.hxz.baseui.util

import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.TextureView
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * @title com.hxz.baseui.util  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des FileUtils
 * @DATE 2020/5/29  9:34 星期五
 */
object FileUtils {

    /**
     * Return whether sdcard is enabled by environment.
     *
     * @return `true`: enabled<br></br>`false`: disabled
     */
    fun isSDCardEnableByEnvironment(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }

    /**
     * Create a file if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return `true`: exists or creates successfully<br></br>`false`: otherwise
     */
    fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        return if (!createOrExistsDir(file.parentFile)) false else try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return `true`: exists or creates successfully<br></br>`false`: otherwise
     */
    fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    /**
     * Return the file by path.
     *
     * @param filePath The path of file.
     * @return the file
     */
    fun getFileByPath(filePath: String?): File? {
        return if (TextUtils.isEmpty(filePath)) null else File(filePath)
    }

    /**
     * Write file from string.
     *
     * @param filePath The path of file.
     * @param content  The string of content.
     * @param append   True to append, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromString(
        filePath: String?,
        content: String?,
        append: Boolean
    ): Boolean {
        return writeFileFromString(getFileByPath(filePath), content, append)
    }

    /**
     * Write file from string.
     *
     * @param file    The file.
     * @param content The string of content.
     * @param append  True to append, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromString(
        file: File?,
        content: String?,
        append: Boolean
    ): Boolean {
        if (file == null || content == null) return false
        if (!createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <$file> failed.")
            return false
        }
        var bw: BufferedWriter? = null
        return try {
            bw = BufferedWriter(FileWriter(file, append))
            bw.write(content)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } finally {
            try {
                bw?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}