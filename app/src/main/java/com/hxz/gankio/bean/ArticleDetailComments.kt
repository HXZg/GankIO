package com.hxz.gankio.bean

import com.google.gson.Gson
import com.hxz.basehttp.bean.ArticleDetailBean
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.basehttp.bean.Comments
import com.hxz.baseui.util.LogUtils

data class ArticleDetailComments(val detail: ArticleDetailBean?,val comments : MutableList<Comments>) {
    companion object {
        fun getDetailComments(detail: BaseResponseBean<ArticleDetailBean>,comments: BaseResponseBean<ArrayList<String>>) : ArticleDetailComments {
            val list = if (comments.data.isNullOrEmpty()) listOf<Comments>() else {
                comments.data!!.map { Gson().fromJson(it,Comments::class.java) }
            }
            val comment_data = arrayListOf<Comments>()
            comment_data.addAll(list)
            LogUtils.i(detail.data,comment_data.size)
            return ArticleDetailComments(detail = detail.data, comments = comment_data)
        }
    }
}