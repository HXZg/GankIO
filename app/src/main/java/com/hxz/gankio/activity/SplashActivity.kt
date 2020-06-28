package com.hxz.gankio.activity

import com.hxz.baseui.view.BaseActivity
import com.hxz.gankio.MainActivity
import com.hxz.gankio.R
import com.hxz.gankio.utils.startTActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * @title com.hxz.gankio.activity  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des SplashActivity
 * @DATE 2020/6/28  16:11 星期日
 */
class SplashActivity : BaseActivity() {
    override fun bindLayout(): Int = R.layout.activity_splash

    override fun initData() {
        tv_g.postDelayed({
            startTActivity<MainActivity>()
        },3000)
    }
}