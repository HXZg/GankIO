package com.hxz.baseui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hxz.baseui.base.IBaseView

/**
 * @title com.hxz.baseui.view  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BaseActivity
 * @DATE 2020/5/29  9:15 星期五
 */
abstract class BaseActivity : AppCompatActivity(),IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeBindLayout(savedInstanceState)
        val res = bindLayout()
        if (res > 0) setContentView(res)
        initData()
    }

}