package com.hxz.gankio.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxz.baseui.view.BaseMFragment
import com.hxz.gankio.R
import com.hxz.gankio.adapter.HomeAdapter
import com.hxz.gankio.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @title com.hxz.gankio.fragment  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des HomeFragment
 * @DATE 2020/5/29  17:34 星期五
 */
class HomeFragment : BaseMFragment<HomeViewModel>() {

    private val homeAdapter by lazy { HomeAdapter(context!!) }

    override fun createVM(): HomeViewModel = viewModels<HomeViewModel>().value

    override fun bindLayout(): Int = R.layout.fragment_home

    override fun initData() {
        initRvHome()
        viewModel.getHomeData()
        initLiveObserve()
    }

    private fun initRvHome() {
        rv_home.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv_home.adapter = homeAdapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        homeAdapter.hiddenChange(hidden)
    }

    private fun initLiveObserve() {
        viewModel.homeLive.observe(this, Observer {
            homeAdapter.setHomeBean(it)
        })
    }

    override fun other(any: Any?) {
        if (any == 0) {  // 因为可以只显示部分数据，所以没做处理
            // 数据获取出错  显示错误页面
        }
    }
}