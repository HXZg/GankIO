package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.repository.ArticleRepository

class ArticleViewModel : BaseViewModel() {

    private val refreshLive = MutableLiveData<Boolean>()

    private val repository by lazy { ArticleRepository() }

    val articleTypeLive = Transformations.switchMap(refreshLive) {
        getArticleType()
    }

    private fun getArticleType() = repository.getArticleList()

    fun refreshData() {
        refreshLive.value = true
    }
}