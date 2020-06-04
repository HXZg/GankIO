package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * @title com.hxz.gankio.viewmodel  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des MainViewModel
 * @DATE 2020/6/4  9:57 星期四
 */
class MainViewModel(private val handle : SavedStateHandle) : ViewModel() {

    private val INDEX_PAGE = "main_index"

    fun saveIndex(index: Int) {
        handle.set(INDEX_PAGE,index)
    }

    fun getIndexPage() : MutableLiveData<Int> = handle.getLiveData(INDEX_PAGE)
}