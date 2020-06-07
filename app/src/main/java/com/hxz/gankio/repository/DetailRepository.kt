package com.hxz.gankio.repository

import com.hxz.basehttp.bean.ArticleDetailBean
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.gankio.bean.ArticleDetailComments
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class DetailRepository : BaseRepository() {

    private suspend fun getArticleDetail(id: String) = api.articleDetail(id)

    private suspend fun getComments(id: String) = api.articleComments(id)

    fun getDetailComments(id: String) = fire {
        coroutineScope {
            val detail = async { getArticleDetail(id) }
            val comments = async { getComments(id) }
            BaseResponseBean.success(ArticleDetailComments.getDetailComments(detail.await(),comments.await()))
        }
    }
}