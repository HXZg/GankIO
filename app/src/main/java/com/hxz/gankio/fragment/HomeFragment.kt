package com.hxz.gankio.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxz.baseui.view.BaseMFragment
import com.hxz.gankio.R
import com.hxz.gankio.activity.ArticleListActivity
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

    private val homeAdapter by lazy { HomeAdapter(requireContext()) }

    override fun createVM(): HomeViewModel = viewModels<HomeViewModel>().value

    override fun bindLayout(): Int = R.layout.fragment_home

    override fun initData() {
        initRvHome()
        initObserve()
        viewModel.setPage(1)
    }

    private fun initRvHome() {
        rv_home.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv_home.adapter = homeAdapter

        homeAdapter.click = {type,msg ->
            when(type) {
                HomeAdapter.ITEM_BANNER -> {}
                HomeAdapter.ITEM_EMPTY -> viewModel.setPage(1)
                HomeAdapter.ITEM_GIRL -> ArticleListActivity.startArticleList(requireContext(),msg,msg)
                HomeAdapter.ITEM_ARTICLE -> {}
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        homeAdapter.hiddenChange(hidden)
    }

    private fun initObserve() {
        viewModel.homeDataLive.observe(this, Observer {
            if (it.data != null) homeAdapter.setHomeBean(it.data!!)
        })
    }
}