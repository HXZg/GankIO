package com.hxz.gankio.repository

class GankRepository : BaseRepository() {

    fun getGankType() = fire { api.getCategoryType(CATEGORY_GANK) }
}