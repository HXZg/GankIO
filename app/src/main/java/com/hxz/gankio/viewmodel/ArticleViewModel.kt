package com.hxz.gankio.viewmodel

import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.repository.ArticleRepository

class ArticleViewModel : BaseViewModel() {

    private val repository by lazy { ArticleRepository() }

    fun getArticleType() = repository.getArticleList()
}