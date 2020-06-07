package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hxz.baseui.util.LogUtils
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.bean.SearchBean
import com.hxz.gankio.repository.SearchRepository

class SearchViewModel : BaseViewModel() {

    private var category = "All"
    private var type = "All"

    private val repository = SearchRepository()
    private val searchLive = MutableLiveData<SearchBean>()
    private val categoryLive = MutableLiveData<Boolean>()

    val typeLiveData = Transformations.switchMap(categoryLive) {
        repository.getCategoryType()
    }
    val listLive = Transformations.switchMap(searchLive) {
        showLoading("加载中。。。")
        repository.search(it.search,it.category,it.type,it.page)
    }

    fun search(search: String,category: String = searchLive.value?.category ?: this.category,
               type: String = searchLive.value?.type ?: this.type,page: Int = 1) : Boolean{
        this.category = category
        this.type = type
        val value = searchLive.value
        if (value == null) {
            if (search.isNotEmpty()) {
                searchLive.value = SearchBean(search,category, type, page)
                return true
            }
        } else if (search != value.search || category != value.category || type != value.type || page != value.page) {
            searchLive.value = SearchBean(search, category, type, page)
            return true
        }
        return false
    }

    fun getSearchType() = searchLive.value?.type ?: type
    fun getSearchCategory() = searchLive.value?.category ?: category

    fun getCategoryType() {
        categoryLive.value = true
    }
}