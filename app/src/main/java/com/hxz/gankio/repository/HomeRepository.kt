package com.hxz.gankio.repository

import com.hxz.basehttp.bean.BannerBean
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.gankio.bean.HomeBean

class HomeRepository : BaseRepository() {

    private suspend fun getBanner() : BaseResponseBean<ArrayList<BannerBean>> = cacheException { api.getBanners() }

    private suspend fun getGirlBean() = cacheException { api.getCategoryType(CATEGORY_GIRL) }

    suspend fun getArticleList(page: Int) = cacheException { api.getArticleList(CATEGORY_ALL,
        CATEGORY_ALL,page,PAGE_COUNT) }


}