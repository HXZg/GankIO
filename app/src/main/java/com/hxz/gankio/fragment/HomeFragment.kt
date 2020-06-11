package com.hxz.gankio.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hxz.baseui.view.BaseMFragment
import com.hxz.baseui.view.WebViewActivity
import com.hxz.gankio.R
import com.hxz.gankio.activity.ArticleListActivity
import com.hxz.gankio.activity.DetailActivity
import com.hxz.gankio.adapter.HomeAdapter
import com.hxz.gankio.adapter.setGankManager
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

    private var currentPage = 1

    override fun createVM(): HomeViewModel = viewModels<HomeViewModel>().value

    override fun bindLayout(): Int = R.layout.fragment_home

    override fun initData() {
        initRvHome()
        initObserve()
        initRefresh()
//        viewModel.setPage(1)
    }

    private fun initRvHome() {
        rv_home.setGankManager(homeAdapter)

        homeAdapter.click = {type,msg ->
            when(type) {
                HomeAdapter.ITEM_BANNER -> WebViewActivity.startWebView(requireContext(),msg)
                HomeAdapter.ITEM_EMPTY -> viewModel.setPage(1)
                HomeAdapter.ITEM_GIRL -> ArticleListActivity.startArticleList(requireContext(),msg,msg)
                HomeAdapter.ITEM_ARTICLE -> DetailActivity.startDetail(requireContext(),msg)
            }
        }
    }

    private fun initRefresh() {
        smart_refresh.setOnRefreshListener { viewModel.setPage(1) }
        smart_refresh.setOnLoadMoreListener { viewModel.setPage(++currentPage) }
        smart_refresh.autoRefresh()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        homeAdapter.hiddenChange(hidden)
    }

    private fun initObserve() {
        viewModel.homeDataLive.observe(this, Observer {
            currentPage = it.data?.page ?: 1
            if (it.data?.page == 1) smart_refresh.finishRefresh() else smart_refresh.finishLoadMore()
            if (it.data?.page_count == it.data?.page) {
                smart_refresh.setEnableLoadMore(false)
            }
            if (it.data != null) homeAdapter.setHomeBean(it.data!!)
        })
    }
}