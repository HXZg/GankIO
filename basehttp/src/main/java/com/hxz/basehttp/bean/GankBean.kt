package com.hxz.basehttp.bean

/**
 * @title com.hxz.basehttp.bean  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des GankBean
 * @DATE 2020/5/29  14:31 星期五
 */

data class BannerBean(val image: String,val title: String,val url: String)

data class CategoryTypeBean(val _id: String,val coverImageUrl: String,val desc: String,val title: String,val type: String)

// 文章列表
data class ArticleListBean(val _id: String,val author: String,val category: String,val createdAt: String,val desc: String,
                            val images: List<String>,val likeCounts: Int,val publishedAt: String,val stars: Int,val title: String,
                            val type: String,val url: String,val views: Int)

