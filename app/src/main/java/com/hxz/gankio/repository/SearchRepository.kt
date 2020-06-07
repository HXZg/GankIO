package com.hxz.gankio.repository

class SearchRepository: BaseRepository() {

    fun search(search: String,category:String,type: String,page: Int) = fire {
        api.searchList(search,category,type,page, PAGE_COUNT)
    }

    fun getCategoryType() = fire {
        api.getCategoryType(CATEGORY_ARTICLE)
    }
}