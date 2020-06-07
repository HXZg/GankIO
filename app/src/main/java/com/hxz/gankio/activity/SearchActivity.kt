package com.hxz.gankio.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.style.SuggestionSpan
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxz.baseui.util.LogUtils
import com.hxz.baseui.view.BaseActivity
import com.hxz.baseui.view.BaseMActivity
import com.hxz.gankio.R
import com.hxz.gankio.adapter.ListAdapter
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

    private val listAdapter by lazy { ListAdapter() }

    private var searchText = ""

    private var popupMenu : PopupMenu? = null

    override fun createVM(): SearchViewModel {
        return viewModels<SearchViewModel>().value
    }

    override fun bindLayout(): Int = R.layout.activity_search

    override fun initData() {
        initToolBar()
        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = listAdapter
        initObserve()
        viewModel.getCategoryType()
    }

    private fun initToolBar() {
        setSupportActionBar(tool_bar)
        tool_bar.setNavigationIcon(R.drawable.ic_back_wh)
        tool_bar.setNavigationOnClickListener { finish() }
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
            listAdapter.setNewData(it.data ?: arrayListOf())
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