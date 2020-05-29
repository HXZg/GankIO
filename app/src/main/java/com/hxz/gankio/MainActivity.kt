package com.hxz.gankio

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hxz.baseui.view.BaseActivity

class MainActivity : BaseActivity() {

    private val fragmentList = arrayListOf<Fragment>()

    override fun beforeBindLayout(saveInstanceState: Bundle?) {

    }

    override fun bindLayout(): Int = R.layout.activity_main

    override fun initData() {
    }

    private fun switchFragment(index: Int) {

    }

}
