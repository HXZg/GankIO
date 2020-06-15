package com.hxz.gankio

import android.content.Intent
import android.graphics.Color
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.hxz.baseui.util.LogUtils
import com.hxz.baseui.view.BaseActivity
import com.hxz.gankio.activity.HotActivity
import com.hxz.gankio.activity.SearchActivity
import com.hxz.gankio.activity.SettingActivity
import com.hxz.gankio.fragment.ArticleFragment
import com.hxz.gankio.fragment.GankFragment
import com.hxz.gankio.fragment.HomeFragment
import com.hxz.gankio.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : BaseActivity() {

    private val mViewModel = viewModels<MainViewModel>()

    private val fragmentList = Array<Fragment?>(3){null}
    private val titles = arrayOf(R.string.home_text,R.string.gank_text,R.string.article_text)

    override fun bindLayout(): Int = R.layout.activity_main

    override fun initData() {
        setSupportActionBar(tool_bar)
        /*rg_main.forEachIndexed { index, view ->
            view.id = index
        }
        rg_main.setOnCheckedChangeListener { group, checkedId ->
            mViewModel.value.saveIndex(checkedId)
        }*/
        btv_main.setOnNavigationItemSelectedListener {
            val index = when(it.itemId){
                R.id.main_home -> 0
                R.id.main_gank -> 1
                else -> 2
            }
            mViewModel.value.saveIndex(index)
            true
        }
        mViewModel.value.getIndexPage().observe(this, Observer {
            if (it != null && it in 0..2) switchFragment(it)
        })
        /*rg_main.check(0)*/
        btv_main.selectedItemId = R.id.main_home
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.main_search -> startActivity(Intent(this,SearchActivity::class.java))
            R.id.main_hot -> startActivity(Intent(this,HotActivity::class.java))
            R.id.main_set -> startActivity(Intent(this,SettingActivity::class.java))
        }
        return true
    }

    private fun switchFragment(index: Int) {
        tool_bar.setSubtitle(titles[index])
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
