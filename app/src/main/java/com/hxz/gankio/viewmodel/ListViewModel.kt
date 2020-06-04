package com.hxz.gankio.viewmodel

import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.repository.ListRepository

class ListViewModel : BaseViewModel() {

    private val repository = ListRepository()

    fun getArticleList(category: String,type: String,page: Int) = repository.getArticleList(category,type, page)
}