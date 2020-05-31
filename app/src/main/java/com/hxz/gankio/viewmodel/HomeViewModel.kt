package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hxz.basehttp.bean.BannerBean
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.repository.BaseRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    val homeLive = MutableLiveData<ArrayList<BannerBean>>()

    val repository = BaseRepository()

    fun getBanner() {
        viewModelScope.launch {
            val join = async { repository.cacheException { repository.api.getBanners() } }
            homeData(join.await())
        }
    }

    private fun homeData(bean: BaseResponseBean<ArrayList<BannerBean>>) {
        if (bean.isSuccess()) homeLive.postValue(bean.data)
    }
}