package com.hxz.gankio.activity

import android.content.Context
import android.content.Intent
import androidx.fragment.app.commit
import com.hxz.baseui.view.BaseActivity
import com.hxz.gankio.R
import com.hxz.gankio.fragment.ListFragment

class ArticleListActivity : BaseActivity() {

    companion object {
        fun startArticleList(context: Context,category: String,type: String) {
            context.startActivity(Intent(context,ArticleListActivity::class.java).apply {
                putExtra(ListFragment.LIST_CATEGORY,category)
                putExtra(ListFragment.LIST_TYPE,type)
            })
        }
    }

    private val category by lazy { intent.getStringExtra(ListFragment.LIST_CATEGORY) }
    private val type by lazy { intent.getStringExtra(ListFragment.LIST_TYPE) }

    override fun bindLayout(): Int = R.layout.activity_article_list

    override fun initData() {
        val fragment =
            supportFragmentManager.findFragmentByTag(getFragmentTag(category, type)) ?: ListFragment.newListFragment(category,type)
        supportFragmentManager.commit {
            replace(R.id.fl_article,fragment,getFragmentTag(category, type))
        }
    }

    private fun getFragmentTag(category: String,type: String) : String {
        return "article:$category  $type"
    }

}