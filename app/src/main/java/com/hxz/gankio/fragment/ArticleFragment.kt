package com.hxz.gankio.fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.hxz.baseui.view.BaseFragment
import com.hxz.gankio.R
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : BaseFragment() {

    override fun bindLayout(): Int  = R.layout.fragment_article

    override fun initData() {
        rv_article.layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)

    }
}