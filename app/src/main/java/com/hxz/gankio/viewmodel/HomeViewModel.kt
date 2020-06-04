package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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

    private val repository = HomeRepository()

    fun getHomeData() = repository.getHomeData()

    fun loadData(page: Int) = repository.loadData(page)
}