package com.hxz.gankio

import androidx.activity.viewModels
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.hxz.baseui.view.BaseActivity
import com.hxz.gankio.fragment.ArticleFragment
import com.hxz.gankio.fragment.GankFragment
import com.hxz.gankio.fragment.HomeFragment
import com.hxz.gankio.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mViewModel = viewModels<MainViewModel>()

    private val fragmentList = Array<Fragment?>(3){null}

    override fun bindLayout(): Int = R.layout.activity_main

    override fun initData() {
        rg_main.forEachIndexed { index, view ->
            view.id = index
        }
        rg_main.setOnCheckedChangeListener { group, checkedId ->
            mViewModel.value.saveIndex(checkedId)
        }
        mViewModel.value.getIndexPage().observe(this, Observer {
            if (it != null && it in 0..2) switchFragment(it)
        })
        rg_main.check(0)
    }

    private fun switchFragment(index: Int) {
        supportFragmentManager.commit {
            fragmentList.forEach { if (it != null) hide(it) }
            var f = fragmentList[index]
            if (f != null) {
                show(f)
            } else {
                f = supportFragmentManager.findFragmentByTag(getFragmentTag(index))
                if (f == null) {
                    f = getFragment(index)
                    add(R.id.fl_main,f,getFragmentTag(index))
                }else {
                    show(f)
                }
                fragmentList[index] = f

            }
        }
    }

    private fun getFragment(index: Int) : Fragment {
        return when(index) {
            1 -> GankFragment()
            2 -> ArticleFragment()
            else -> HomeFragment()
        }
    }

    private fun getFragmentTag(index: Int) : String {
        return "main fragment:$index"
    }
}
