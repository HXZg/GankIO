package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.basehttp.bean.BannerBean
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.basehttp.bean.CategoryTypeBean
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.bean.HomeBean
import com.hxz.gankio.repository.BaseRepository
import com.hxz.gankio.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val pageLive = MutableLiveData<Int>()

    private val repository = HomeRepository()

    val homeDataLive = Transformations.switchMap(pageLive) { page ->
        if (page == 1) getHomeData() else loadData(page)
    }

    private fun getHomeData() = repository.getHomeData()

    private fun loadData(page: Int) = repository.loadData(page)

    fun setPage(page: Int) {
        pageLive.value = page
    }
}