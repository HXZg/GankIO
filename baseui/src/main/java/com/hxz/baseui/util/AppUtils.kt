package com.hxz.baseui.util

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process
import android.text.TextUtils
import androidx.annotation.StringRes
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * @title com.hxz.baseui.util  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des AppUtils
 * @DATE 2020/5/29  9:17 星期五
 */
object AppUtils {

    lateinit var app: Application
    private set

    private val activityList = arrayListOf<Activity>()

    fun init(app: Application) {
        this.app = app
        app.registerActivityLifecycleCallbacks(AppLifecycle())
    }

    fun getStringByRes(@StringRes res: Int,vararg format: Any) = app.getString(res,format)

    /**
     * Return the application's version name.
     *
     * @return the application's version name
     */
    fun getAppVersionName(): String? {
        return getAppVersionName(app.packageName)
    }

    /**
     * Return the application's version name.
     *
     * @param packageName The name of the package.
     * @return the application's version name
     */
    fun getAppVersionName(packageName: String?): String? {
        return if (TextUtils.isEmpty(packageName)) "" else try {
            val pm: PackageManager = app.packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            pi?.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Return the application's version code.
     *
     * @return the application's version code
     */
    fun getAppVersionCode(): Int {
        return getAppVersionCode(app.packageName)
    }

    /**
     * Return the application's version code.
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    fun getAppVersionCode(packageName: String?): Int {
        return if (TextUtils.isEmpty(packageName)) -1 else try {
            val pm: PackageManager = app.packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            pi?.versionCode ?: -1
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            -1
        }
    }

    /**
     * Return the name of current process.
     *
     * @return the name of current process
     */
    fun getCurrentProcessName(): String? {
        var name = getCurrentProcessNameByFile()
        if (!TextUtils.isEmpty(name)) return name
        name = getCurrentProcessNameByAms()
        if (!TextUtils.isEmpty(name)) return name
        name = getCurrentProcessNameByReflect()
        return name
    }

    private fun getCurrentProcessNameByFile(): String? {
        return try {
            val file =
                File("/proc/" + Process.myPid() + "/" + "cmdline")
            val mBufferedReader =
                BufferedReader(FileReader(file))
            val processName = mBufferedReader.readLine().trim { it <= ' ' }
            mBufferedReader.close()
            processName
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun getCurrentProcessNameByAms(): String? {
        try {
            val am = app
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                ?: return ""
            val info = am.runningAppProcesses
            if (info == null || info.size == 0) return ""
            val pid = Process.myPid()
            for (aInfo in info) {
                if (aInfo.pid == pid) {
                    if (aInfo.processName != null) {
                        return aInfo.processName
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            return ""
        }
        return ""
    }

    private fun getCurrentProcessNameByReflect(): String? {
        var processName = ""
        try {
            val loadedApkField = app.javaClass.getField("mLoadedApk")
            loadedApkField.isAccessible = true
            val loadedApk = loadedApkField[app]
            val activityThreadField =
                loadedApk.javaClass.getDeclaredField("mActivityThread")
            activityThreadField.isAccessible = true
            val activityThread = activityThreadField[loadedApk]
            val getProcessName =
                activityThread.javaClass.getDeclaredMethod("getProcessName")
            processName = getProcessName.invoke(activityThread) as String
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return processName
    }

    class AppLifecycle : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            activityList.remove(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityList.add(activity)
        }

        override fun onActivityResumed(activity: Activity) {
        }

    }
}