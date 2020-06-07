package com.hxz.basehttp.api

import com.hxz.basehttp.bean.*
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @title com.hxz.basehttp.api  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des GankApi
 * @DATE 2020/5/29  11:48 星期五
 */
interface GankApi {

    /**
     * 首页轮播
     */
    @GET("banners")
    suspend fun getBanners() : BaseResponseBean<ArrayList<BannerBean>>

    /**
     * 获取对应子分类
     * @param type Article | GanHuo | Girl
     */
    @GET("categories/{category_type}")
    suspend fun getCategoryType(@Path("category_type") type: String) : BaseResponseBean<ArrayList<CategoryTypeBean>>

    /**
     * 获取分类下的数据
     * article 可接受参数 All(所有分类) | Article | GanHuo | Girl
    type 可接受参数 All(全部类型) | Android | iOS | Flutter | Girl ...，即分类API返回的类型数据
    count: [10, 50]
    page: >=1
     */
    @GET("data/category/{category}/type/{type}/page/{page}/count/{count}")
    suspend fun getArticleList(@Path("category") category: String,@Path("type") type: String,
                                @Path("page") page: Int,@Path("count") count: Int) : BaseResponseBean<ArrayList<ArticleListBean>>

    /**
     * 本周最热
     * hot_type 可接受参数 views（浏览数） | likes（点赞数） | comments（评论数）❌
    article 可接受参数 Article | GanHuo | Girl
    count: [1, 20]
     */
    @GET("hot/{hot_type}/category/{article}/count/{count}")
    suspend fun getHotList(@Path("hot_type") hot_type: String,
        @Path("article") category: String,@Path("count") count: Int) : BaseResponseBean<ArrayList<ArticleListBean>>

    /**
     * 搜索api
     * search 可接受参数 要搜索的内容
    article 可接受参数 All[所有分类] | Article | GanHuo
    type 可接受参数 Android | iOS | Flutter ...，即分类API返回的类型数据
    count: [10, 50]
    page: >=1
     */
    @GET("search/{search}/category/{article}/type/{type}/page/{page}/count/{count}")
    suspend fun searchList(@Path("search") search: String,
        @Path("article") category: String,@Path("type") type: String,
        @Path("page") page: Int,@Path("count") count: Int) : BaseResponseBean<ArrayList<ArticleListBean>>


    // 文章评论  文章详情  随机

    @GET("post/{post_id}")
    suspend fun articleDetail(id: String) : BaseResponseBean<ArticleDetailBean>

    @GET("post/comments/{post_id}")
    suspend fun articleComments(id: String) : BaseResponseBean<ArrayList<String>>

}