package com.hxz.gankio

import android.os.Bundle
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.hxz.baseui.view.BaseActivity
import com.hxz.gankio.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val fragmentList = arrayListOf<Fragment>()

    override fun beforeBindLayout(saveInstanceState: Bundle?) {

    }

    override fun bindLayout(): Int = R.layout.activity_main

    override fun initData() {
        rg_main.forEachIndexed { index, view ->
            view.id = index
        }
        rg_main.setOnCheckedChangeListener { group, checkedId ->
            switchFragment(checkedId)
        }
    }

    private fun switchFragment(index: Int) {
        val fragment = if (fragmentList.size <= index) getFragment(index) else fragmentList[index]
        fragmentList.forEach { supportFragmentManager.beginTransaction().hide(it).commit() }
        supportFragmentManager.beginTransaction().add(R.id.fl_main,fragment).commit()
    }

    private fun getFragment(index: Int) = when(index) {
        0 -> HomeFragment()
        else -> HomeFragment()
    }
}
