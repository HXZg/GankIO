package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.repository.GankRepository

class GankViewModel(private val handle : SavedStateHandle) : BaseViewModel() {

    private val GANK_PAGE = "gank_index"
    private val repository = GankRepository()

    private val refreshLive = MutableLiveData<Boolean>()

    val gankTypeLive = Transformations.switchMap(refreshLive) {
        getGankType()
    }

    private fun getGankType() = repository.getGankType()

    fun savePage(index: Int) {
        handle.set(GANK_PAGE,index)
    }

    fun getGankPage() = handle.get<Int>(GANK_PAGE) ?: 0

    fun refreshData() {
        refreshLive.value = true
    }
}