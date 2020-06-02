package com.hxz.gankio.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        emptyView = getEmptyView(context)
    }

    private var isShowError = false

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

    private fun getEmptyView(context: Context) : View {
        return TextView(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1,-1)
            textSize = 16f
            setTextColor(Color.BLACK)
            setBackgroundColor(Color.WHITE)
            text = "没有获取到数据噢~~~"
        }
    }

    fun setHomeBean(bean: HomeBean) {
//        if (bean.isSuccess)
        if (bean.bannerList.isNotEmpty()) bannerView.refreshData(bean.bannerList) else removeHeaderView(bannerView)
        if (bean.girlList.isNotEmpty()) imageView.loadUrl(bean.girlList[0].coverImageUrl) else removeHeaderView(imageView)
        setNewData(bean.articleList)
    }


}