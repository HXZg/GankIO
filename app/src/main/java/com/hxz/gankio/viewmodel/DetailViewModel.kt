package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hxz.baseui.util.LogUtils
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.repository.DetailRepository

class DetailViewModel : BaseViewModel() {

    private val getDetailList = MutableLiveData<String>()
    private val repository = DetailRepository()

    val detailCommentsLive = Transformations.switchMap(getDetailList) {
        showLoading("加载中。。。")
        repository.getDetailComments(it)
    }

    fun getDetail(id: String) {
        getDetailList.value = id
    }
}