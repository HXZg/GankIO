package com.hxz.baseui.view

import android.os.Bundle
import android.widget.Toast
import com.hxz.baseui.base.IBaseVM
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.baseui.widght.LoadingDialog

/**
 * @title com.hxz.baseui.view  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BaseMFragment
 * @DATE 2020/5/29  10:57 星期五
 */
abstract class BaseMFragment<VM: BaseViewModel> : BaseFragment(),IBaseVM<VM> {

    protected val viewModel by lazy { createVM() }

    protected val mLoading : LoadingDialog by lazy {
        LoadingDialog(context!!).apply {
            initLoading()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLiveEvent(viewModel,this)
    }

    override fun showDialog(msg: String?) {
        if (mLoading.isShowing) return
        mLoading.setMessage(msg ?: "")
        mLoading.show()
    }

    override fun dismissDialog() {
        if (mLoading.isShowing) mLoading.dismiss()
    }

    override fun showToast(msg: String) {
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }

    override fun finishThis() {
        activity?.finish()
    }

    override fun other(any: Any?) {

    }

    override fun LoadingDialog.initLoading() {

    }
}