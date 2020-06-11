package com.hxz.gankio.activity

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.hxz.baseui.util.LogUtils
import com.hxz.baseui.view.BaseActivity
import com.hxz.gankio.R
import com.hxz.gankio.adapter.BaseGankAdapter
import com.hxz.gankio.adapter.setGankManager
import com.hxz.gankio.repository.BaseRepository
import com.hxz.gankio.viewmodel.HotViewModel
import kotlinx.android.synthetic.main.activity_hot.*

/**
 * @title com.hxz.gankio.activity  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des HotActivity
 * @DATE 2020/6/5  11:25 星期五
 */
class HotActivity : BaseActivity() {

    private val viewModel by lazy { viewModels<HotViewModel>().value }
    private val adapter by lazy { rv_hot_list.adapter as BaseGankAdapter }

    override fun bindLayout(): Int = R.layout.activity_hot

    override fun initData() {
        initToolBar()
        initRv()
        initObserve()
    }

    private fun initToolBar() {
        setSupportActionBar(tool_bar)
        supportActionBar?.title = "本周最火"
        tool_bar.setNavigationIcon(R.drawable.ic_back_wh)
        tool_bar.setNavigationOnClickListener { finish() }
    }

    private fun initRv() {
        rv_hot_list.setGankManager()

        adapter.setClickInvoke { position, data ->
            DetailActivity.startDetail(this,data._id)
        }

//        smart_hot.setOnRefreshListener { refreshList() }
//        smart_hot.autoRefresh()
        refreshList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hot_menu,menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val start = when(viewModel.getHotType()) {
            "views" -> "浏览数"
            "comments" -> "评论数"
            else -> "点赞数"
        }
        val end = when(viewModel.getHotCategory()) {
            BaseRepository.CATEGORY_GANK -> "干货"
            BaseRepository.CATEGORY_GIRL -> "妹纸"
            else -> "文章"
        }
        menu?.findItem(R.id.hot_category)?.title = end
        menu?.findItem(R.id.hot_type)?.title = start
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.hot_article -> refreshList(c = BaseRepository.CATEGORY_ARTICLE)
            R.id.hot_ganhuo -> refreshList(c = BaseRepository.CATEGORY_GANK)
            R.id.hot_girl -> refreshList(c = BaseRepository.CATEGORY_GIRL)
            R.id.hot_views -> refreshList(t = "views")
            R.id.hot_likes -> refreshList(t = "likes")
            R.id.hot_comments -> refreshList(t = "comments")
        }
        return true
    }

    private fun initObserve() {
        viewModel.hotListLive.observe(this, Observer {
            smart_hot.finishRefresh()
            adapter.setNewData(it.data ?: arrayListOf())
        })
        /*viewModel.titleLive.observe(this, Observer {
            supportActionBar?.title = it
        })*/
    }

    private fun refreshList(c: String = viewModel.getHotCategory() ?: BaseRepository.CATEGORY_ARTICLE,t: String = viewModel.getHotType() ?: "views") {
        val category = viewModel.getHotCategory()
        val type = viewModel.getHotType()
        if (category != c || type != t) {
            LogUtils.i(category,type,c,t)
            viewModel.setHotType(t)
            viewModel.setHotCategory(c)
            invalidateOptionsMenu()
        }
    }

}