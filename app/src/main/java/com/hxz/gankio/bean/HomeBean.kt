package com.hxz.gankio.bean

import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.basehttp.bean.BannerBean
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.basehttp.bean.CategoryTypeBean

class HomeBean private constructor() {

    companion object {
        fun getHomeBean(banner: BaseResponseBean<ArrayList<BannerBean>>,
                        girl: BaseResponseBean<ArrayList<CategoryTypeBean>>,
                        article: BaseResponseBean<ArrayList<ArticleListBean>>,
                        error: (bm: String,gm: String,am: String) -> Unit = {_,_,_ ->}) : HomeBean {
            return HomeBean().apply {
                if (banner.isSuccess()) bannerList.addAll(banner.data!!)
                if (girl.isSuccess()) girlList.addAll(girl.data!!)
                if (article.isSuccess()) {
                    page = article.page
                    page_count = article.page_count
                    articleList.addAll(article.data!!)
                }
                if (!banner.isSuccess() || !girl.isSuccess() || !article.isSuccess()) {
                    isSuccess = false
                    error(banner.errorMsg,girl.errorMsg,article.errorMsg)
                }
            }
        }

        fun getLoadHomeBean(article: BaseResponseBean<ArrayList<ArticleListBean>>) : HomeBean{
            return HomeBean().apply {
                page = article.page
                page_count = article.page_count
                if (article.isSuccess()) articleList.addAll(article.data!!)
            }
        }
    }

    var isSuccess = true
    var page = 0
    var page_count = 0

    val bannerList = arrayListOf<BannerBean>()
    val girlList = arrayListOf<CategoryTypeBean>()
    val articleList = arrayListOf<ArticleListBean>()


}