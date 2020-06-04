package com.hxz.gankio.repository

import kotlinx.coroutines.coroutineScope

class ArticleRepository : BaseRepository() {

    fun getArticleList() = fire { api.getCategoryType(CATEGORY_ARTICLE) }
}