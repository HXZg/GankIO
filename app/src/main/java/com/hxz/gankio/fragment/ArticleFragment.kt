package com.hxz.gankio.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.hxz.baseui.view.BaseFragment
import com.hxz.gankio.R
import com.hxz.gankio.adapter.CategoryAdapter
import com.hxz.gankio.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : BaseFragment() {

    private val mViewModel by lazy { viewModels<ArticleViewModel>().value }

    private val articleAdapter by lazy { CategoryAdapter() }

    override fun bindLayout(): Int  = R.layout.fragment_article

    override fun initData() {
        rv_article.layoutManager = GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)
        rv_article.adapter = articleAdapter

        mViewModel.getArticleType().observe(this, Observer {
            articleAdapter.setNewData(it.data ?: arrayListOf())
        })
    }
}

