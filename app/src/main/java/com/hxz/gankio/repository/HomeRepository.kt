package com.hxz.gankio.repository

import androidx.lifecycle.liveData
import com.hxz.basehttp.bean.BannerBean
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.gankio.bean.HomeBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class HomeRepository : BaseRepository() {

    private suspend fun getBanner() : BaseResponseBean<ArrayList<BannerBean>> = api.getBanners()

    private suspend fun getGirlBean() = api.getCategoryType(CATEGORY_GIRL)

    private suspend fun getArticleList(page: Int) =
         api.getArticleList(CATEGORY_ALL, CATEGORY_ALL,page,PAGE_COUNT)

    fun getHomeData() = fire {
        coroutineScope {
            val banner = async { getBanner() }
            val girl = async { getGirlBean() }
            val list = async { getArticleList(1) }
            BaseResponseBean.success(HomeBean.getHomeBean(banner.await(),girl.await(),list.await()))
        }
    }

    fun loadData(page: Int) = fire {
        coroutineScope {
            val list = getArticleList(page)
            BaseResponseBean.success(HomeBean.getLoadHomeBean(list))
        }
    }
}