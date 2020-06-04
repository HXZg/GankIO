package com.hxz.gankio.repository

class ListRepository : BaseRepository() {

    fun getArticleList(category: String,type: String,page: Int,count: Int = PAGE_COUNT) = fire {
        api.getArticleList(category, type, page, count)
    }

}