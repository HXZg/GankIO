package com.hxz.baseui.viewmodel

import androidx.lifecycle.ViewModel
import com.hxz.baseui.base.SingleLiveEvent
import com.hxz.baseui.bean.ViewDataResource

/**
 * @title com.hxz.baseui.viewmodel  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BaseViewModel
 * @DATE 2020/5/29  10:17 星期五
 */
open class BaseViewModel : ViewModel() {

    val uiChangeLive = SingleLiveEvent<ViewDataResource>()

    fun showLoading(msg: String? = null) = uiChangeLive.postValue(ViewDataResource.showLoading(msg))
    fun dismissLoading() = uiChangeLive.postValue(ViewDataResource.dismissLoading())
    fun showToast(msg: String) = uiChangeLive.postValue(ViewDataResource.showToast(msg))
    fun finishActivity() = uiChangeLive.postValue(ViewDataResource.finishActivity())
    fun other(msg: Any? = null) = uiChangeLive.postValue(ViewDataResource.other(msg))

}