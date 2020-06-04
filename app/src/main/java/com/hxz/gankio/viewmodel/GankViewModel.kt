package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.repository.GankRepository

class GankViewModel(private val handle : SavedStateHandle) : BaseViewModel() {

    private val GANK_PAGE = "gank_index"
    private val repository = GankRepository()

    fun getGankType() = repository.getGankType()

    fun savePage(index: Int) {
        handle.set(GANK_PAGE,index)
    }

    fun getGankPage() : MutableLiveData<Int> = handle.getLiveData(GANK_PAGE)
}