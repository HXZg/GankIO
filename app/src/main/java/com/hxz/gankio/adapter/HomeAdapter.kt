package com.hxz.gankio.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hxz.banner.Banner
import com.hxz.banner.adapter.BaseBannerAdapter
import com.hxz.banner.adapter.BaseViewHolder
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.basehttp.bean.BannerBean
import com.hxz.basehttp.bean.CategoryTypeBean
import com.hxz.gankio.R
import com.hxz.gankio.utils.dp2px
import com.hxz.gankio.utils.loadUrl

class HomeAdapter(context: Context) : BaseRvAdapter<ArticleListBean>(layout = R.layout.item_home) {

    private val bannerView by lazy { getBannerView(context) }
    private val imageView by lazy { getImageView(context) }

    init {
        addHeaderView(bannerView)
        addHeaderView(imageView)
    }

    override fun convertViewHolder(holder: BaseRvHolder, data: ArticleListBean) {

    }

    private fun getBannerView(context: Context) : Banner<BannerBean> {
        val adapter = object : BaseBannerAdapter<BannerBean>() {
            override fun createView(parent: ViewGroup, viewType: Int): View {
                return ImageView(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1,-1)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            }

            override fun convertHolder(holder: BaseViewHolder, item: BannerBean) {
                (holder.itemView as ImageView).loadUrl(item.url)
            }
        }
        return Banner<BannerBean>(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1,200.dp2px(context))
            // TODO 设置adapter transform 指示器 自动轮播
        }
    }

    private fun getImageView(context: Context) : ImageView {
        return ImageView(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1,200.dp2px(context))
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    fun setHomeBean(bean: HomeBean) {
//        if (bean.isSuccess)
        if (bean.bannerList.isNotEmpty() && bean.girlList.isNotEmpty()) {
            setBannerAndIvData(bean.bannerList,bean.girlList[0])
        } else {
//            removeHeaderView(null)  隐藏头部view
        }
        setNewData(bean.articleList)
    }

    private fun setBannerAndIvData(banner: MutableList<BannerBean>,bean: CategoryTypeBean) {
        bannerView.refreshData(banner)
        imageView.loadUrl(bean.coverImageUrl)
    }
}