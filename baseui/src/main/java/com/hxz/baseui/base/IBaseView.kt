package com.hxz.baseui.base

import android.os.Bundle

/**
 * @title com.hxz.baseui.base  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des IBaseView
 * @DATE 2020/5/29  9:13 星期五
 * 基本 view 接口   （activity / fragment）
 */
interface IBaseView {

    fun bindLayout() : Int

    fun beforeBindLayout(saveInstanceState: Bundle?)

    fun initData()
}