package com.hxz.gankio.repository

class ArticleRepository : BaseRepository() {

    suspend fun getArticleList() = cacheException { api.getCategoryType(CATEGORY_ARTICLE) }
}