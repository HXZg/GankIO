package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.repository.ListRepository

class ListViewModel(category: String,type: String) : BaseViewModel() {

    private val pageLive = MutableLiveData<Int>()

    private val repository = ListRepository()

    val listLive = Transformations.switchMap(pageLive) {
        getArticleList(category,type,it)
    }

    private fun getArticleList(category: String,type: String,page: Int) =
        repository.getArticleList(category,type, page)

    fun setPage(page: Int) {
        pageLive.value = page
    }
}