package com.hxz.basehttp.bean

/**
 * @title com.hxz.basehttp.bean  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BaseResponseBean
 * @DATE 2020/5/29  14:27 星期五
 */
class BaseResponseBean<T> {

    companion object {
        private const val SUCCESS_STATUS = 100
        private const val CUSTOM_ERROR = -1

        fun customError(e: Exception) : BaseResponseBean<Any> {
            return BaseResponseBean<Any>().apply {
                status = CUSTOM_ERROR
                errorMsg = e.message ?: ""
            }
        }
    }

    var status: Int = 0  // 100 success
    var errorMsg: String = ""

    var data: T? = null  // data 数据位

    var page: Int = 0
    var page_count : Int = 0
    var total_counts : Int = 0

    fun isSuccess() = status == SUCCESS_STATUS

    fun isCustomError() = status == CUSTOM_ERROR
}