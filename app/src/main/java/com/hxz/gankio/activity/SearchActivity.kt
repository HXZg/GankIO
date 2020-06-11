package com.hxz.gankio.activity

import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxz.baseui.util.LogUtils
import com.hxz.baseui.view.BaseMActivity
import com.hxz.gankio.R
import com.hxz.gankio.adapter.BaseGankAdapter
import com.hxz.gankio.adapter.setGankManager
import com.hxz.gankio.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

/**
 * @title com.hxz.gankio.activity  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des SearchActivity
 * @DATE 2020/6/5  11:25 星期五
 */
class SearchActivity : BaseMActivity<SearchViewModel>() {

    private val listAdapter by lazy { rv_search.adapter as BaseGankAdapter }

    private var searchText = ""

    private var popupMenu : PopupMenu? = null

    private var currentPage = 1

    override fun createVM(): SearchViewModel {
        return viewModels<SearchViewModel>().value
    }

    override fun bindLayout(): Int = R.layout.activity_search

    override fun initData() {
        initToolBar()
        rv_search.setGankManager()

        listAdapter.setClickInvoke { position, data ->
            DetailActivity.startDetail(this,data._id)
        }
        initObserve()
        initRefresh()
        viewModel.getCategoryType()
    }

    private fun initToolBar() {
        setSupportActionBar(tool_bar)
        tool_bar.setNavigationIcon(R.drawable.ic_back_wh)
        tool_bar.setNavigationOnClickListener { finish() }
    }

    private fun initRefresh() {
        smart_search.setEnableRefresh(false)
        smart_search.setOnLoadMoreListener { viewModel.search(searchText,page = ++currentPage) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val v = menu?.findItem(R.id.search)?.actionView
        if (v != null && v is SearchView) {
            v.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (viewModel.search(query ?: "")) searchText = query ?: ""
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        LogUtils.i("on perpare option menu")
        menu?.findItem(R.id.search_category)?.title = viewModel.getSearchCategory()
        menu?.findItem(R.id.search_type)?.title = viewModel.getSearchType()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.search_all,R.id.search_article,R.id.search_gank -> {
                viewModel.search(searchText,category = item.title.toString())
                invalidateOptionsMenu()
            }
            R.id.search_type -> popupMenu?.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initObserve() {
        viewModel.listLive.observe(this, Observer {
            currentPage = it.page
            if (it.page == 1) {
                smart_search.finishRefresh()
                listAdapter.setNewData(it.data ?: arrayListOf())
            } else {
                smart_search.finishLoadMore()
                listAdapter.loadDataList(it.data ?: arrayListOf())
            }
            if (it.page == it.page_count) smart_search.setEnableLoadMore(false)
            dismissDialog()
        })
        viewModel.typeLiveData.observe(this, Observer {
            popupMenu = PopupMenu(this, tool_bar,Gravity.BOTTOM or Gravity.RIGHT)
            popupMenu?.menu?.add(Menu.NONE,Menu.FIRST,0,"All")
            it.data?.forEachIndexed { index, categoryTypeBean ->
                popupMenu?.menu?.add(Menu.NONE,Menu.FIRST + index + 1,index + 1,categoryTypeBean.type)
            }
            popupMenu?.setOnMenuItemClickListener {item ->
                viewModel.search(searchText,type = item.title.toString())
                invalidateOptionsMenu()
                true
            }
        })
    }
}