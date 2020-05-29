package com.hxz.baseui.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.baseui.widght.LoadingDialog

/**
 * @title com.hxz.baseui.base  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des IBaseVM
 * @DATE 2020/5/29  10:23 星期五
 */
interface IBaseVM<VM: BaseViewModel> {

    fun createVM() : VM

    fun showDialog(msg: String?)

    fun dismissDialog()

    fun showToast(msg: String)

    fun finishThis()

    fun other(any: Any?)

    fun initLiveEvent(vm: VM,owner: LifecycleOwner) {
        vm.uiChangeLive.observe(owner, Observer {
            when {
                it.isShowLoading() -> showDialog(it.msg?.toString())
                it.isDismiss() -> dismissDialog()
                it.isShowToast() -> showToast(it.msg?.toString() ?: "")
                it.isFinish() -> finishThis()
                it.isOther() -> other(it.msg)
            }
        })
    }

    fun LoadingDialog.initLoading()
}