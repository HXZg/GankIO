package com.hxz.gankio.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hxz.banner.Banner
import com.hxz.banner.adapter.BaseBannerAdapter
import com.hxz.banner.transform.GalleryPageTransform
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.basehttp.bean.BannerBean
import com.hxz.gankio.R
import com.hxz.gankio.bean.HomeBean
import com.hxz.gankio.utils.dp2px
import com.hxz.gankio.utils.loadUrl

typealias HomeClickListener = (type: Int,msg: String) -> Unit

class HomeAdapter(context: Context) : BaseGankAdapter() {

    companion object {
        const val ITEM_BANNER = 0
        const val ITEM_GIRL = 1
        const val ITEM_EMPTY = 2
        const val ITEM_ARTICLE = 3
    }

    private val bannerView by lazy { getBannerView(context) }
    private val imageView by lazy { getImageView(context) }

    var click : HomeClickListener? = null

    init {
        addHeaderView(bannerView)
        addHeaderView(imageView)
        emptyView = getEmptyView(context)
        setClickInvoke { position, data ->
            click?.invoke(ITEM_ARTICLE,data._id)
        }
    }

    private fun getBannerView(context: Context) : Banner {
        val adapter = object : BaseBannerAdapter<BannerBean>() {
            override fun createView(parent: ViewGroup, viewType: Int): View {
                return ImageView(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1,-1)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            }

            override fun convertHolder(holder: RecyclerView.ViewHolder, item: BannerBean) {
                (holder.itemView as ImageView).loadUrl(item.image)
            }
        }
        return Banner(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1,200.dp2px(context)).apply {
                topMargin = 10.dp2px(context)
            }
            this.setAdapter(adapter)
            setCanLoop(true)
            setPagerTransform(GalleryPageTransform(20))
            setGallery(60,2)
            setInDecorateColor(Color.BLACK,Color.BLUE)
            setPageClickListener {
                click?.invoke(ITEM_BANNER,adapter.dataList[it].url)
            }
            setAutoPlay(true)
        }
    }

    private fun getImageView(context: Context) : ImageView {
        return ImageView(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1,150.dp2px(context)).apply {
                topMargin = 10.dp2px(context)
                bottomMargin = 5.dp2px(context)
                leftMargin = 10.dp2px(context)
                rightMargin = 10.dp2px(context)
                setOnClickListener {
                    click?.invoke(ITEM_GIRL,"Girl")
                }
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun getEmptyView(context: Context) : View {
        return TextView(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1,-1)
            textSize = 16f
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
            setBackgroundColor(Color.WHITE)
            text = "没有获取到数据噢~~~"
            setOnClickListener {
                click?.invoke(ITEM_EMPTY,"")
            }
        }
    }

    fun setHomeBean(bean: HomeBean) {
//        if (bean.isSuccess)
        if (bean.page <= 1) {
            if (bean.bannerList.isNotEmpty()) bannerView.refreshData(bean.bannerList) else removeHeaderView(bannerView)
            if (bean.girlList.isNotEmpty()) imageView.loadUrl(bean.girlList[0].coverImageUrl) else removeHeaderView(imageView)
            setNewData(bean.articleList)
        } else {
            loadDataList(bean.articleList)
        }

    }

    fun hiddenChange(hidden: Boolean) {
        if (hidden) {
            bannerView.stopLoop()
        } else {
            bannerView.startLoop()
        }
    }


}