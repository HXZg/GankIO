package com.hxz.baseui.bean

/**
 * @title com.hxz.baseui.bean  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des ViewDataResource
 * @DATE 2020/5/29  10:29 星期五
 *
 * 在 view model 中 操作UI
 */
class ViewDataResource(val tag: Int,val msg: Any?) {

    companion object {
        private const val SHOW_LOADING = 0X01
        private const val DISMISS_LOADING = 0X02
        private const val SHOW_TOAST = 0X04
        private const val FINISH_ACTIVITY = 0X05
        private const val OTHER = 0X06

        fun showLoading(msg: String? = null) = ViewDataResource(SHOW_LOADING,msg)
        fun dismissLoading() = ViewDataResource(DISMISS_LOADING,null)
        fun showToast(msg: String) = ViewDataResource(SHOW_TOAST,msg)
        fun finishActivity() = ViewDataResource(FINISH_ACTIVITY,null)
        fun other(msg: Any? = null) = ViewDataResource(OTHER,msg)
    }

    fun isShowLoading() = tag == SHOW_LOADING
    fun isDismiss() = tag == DISMISS_LOADING
    fun isShowToast() = tag == SHOW_TOAST
    fun isFinish() = tag == FINISH_ACTIVITY
    fun isOther() = tag == OTHER
}