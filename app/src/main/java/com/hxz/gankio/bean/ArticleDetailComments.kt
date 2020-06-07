package com.hxz.gankio.bean

import com.google.gson.Gson
import com.hxz.basehttp.bean.ArticleDetailBean
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.basehttp.bean.Comments

data class ArticleDetailComments(val detail: ArticleDetailBean?,val comments : List<Comments>) {
    companion object {
        fun getDetailComments(detail: BaseResponseBean<ArticleDetailBean>,comments: BaseResponseBean<ArrayList<String>>) : ArticleDetailComments {
            val list = if (comments.data.isNullOrEmpty()) listOf<Comments>() else {
                comments.data!!.map { Gson().fromJson(it,Comments::class.java) }
            }
            return ArticleDetailComments(detail = detail.data, comments = list)
        }
    }
}