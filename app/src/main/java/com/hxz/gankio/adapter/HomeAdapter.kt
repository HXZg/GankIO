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

class HomeAdapter : BaseBannerAdapter<Any>() {

    companion object{
        private const val ITEM_TYPE_BANNER = 0
        private const val ITEM_TYPE_GIRL = 1
        private const val ITEM_TYPE_ARTICLE = 2
    }

    override fun createView(parent: ViewGroup, viewType: Int): View {
        return when(viewType) {
            ITEM_TYPE_BANNER -> getBannerView(parent.context)
            ITEM_TYPE_GIRL -> getImageView(parent.context)
            else -> View.inflate(parent.context, R.layout.item_home,null)
        }
    }

    override fun convertHolder(holder: BaseViewHolder, item: Any) {

    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> ITEM_TYPE_BANNER
            1 -> ITEM_TYPE_GIRL
            else -> ITEM_TYPE_ARTICLE
        }
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

    class HomeBean private  constructor(val type: Int) {
        companion object {
            fun getHomeBannerBean(list: ArrayList<BannerBean>) = HomeBean(ITEM_TYPE_BANNER).apply {
                bannerList.addAll(list)
            }


        }
        val bannerList = arrayListOf<BannerBean>()
        val girlBean = arrayListOf<CategoryTypeBean>()
        val articleListBean = arrayListOf<ArticleListBean>()
    }
}