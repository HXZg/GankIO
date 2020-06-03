package com.hxz.gankio.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hxz.banner.Banner
import com.hxz.banner.adapter.BaseBannerAdapter
import com.hxz.banner.transform.GalleryPageTransform
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.basehttp.bean.BannerBean
import com.hxz.gankio.R
import com.hxz.gankio.bean.HomeBean
import com.hxz.gankio.utils.HomeClickListener
import com.hxz.gankio.utils.dp2px
import com.hxz.gankio.utils.loadUrl

class HomeAdapter(context: Context) : BaseRvAdapter<ArticleListBean>(layout = R.layout.item_home) {

    companion object {
        const val ITEM_BANNER = 0
        const val ITEM_IMAGE = 1
        const val ITEM_ARTICLE = 2
    }

    private val bannerView by lazy { getBannerView(context) }
    private val imageView by lazy { getImageView(context) }

    private var click: HomeClickListener? = null

    init {
        addHeaderView(bannerView)
        addHeaderView(imageView)
        emptyView = getEmptyView(context)
    }

    override fun convertViewHolder(holder: BaseRvHolder, data: ArticleListBean) {
        holder.apply {
            if (data.images.isNotEmpty()) holder.findView<ImageView>(R.id.iv_img).loadUrl(data.images[0])
            setText(R.id.tv_title,t = data.title)
            setText(R.id.tv_desc,t = data.desc)
            setText(R.id.tv_time,t = data.publishedAt)
            setText(R.id.tv_auto_type,t = "${data.type}  ${data.author}")
        }
    }

    private fun getBannerView(context: Context) : Banner {
        val adapter = object : BaseBannerAdapter<BannerBean>() {
            override fun createView(parent: ViewGroup, viewType: Int): View {
                return ImageView(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(-1,-1)
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

            }
            setAutoPlay(true)
        }
    }

    private fun getImageView(context: Context) : ImageView {
        return ImageView(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1,200.dp2px(context)).apply {
                topMargin = 10.dp2px(context)
                bottomMargin = 5.dp2px(context)
                leftMargin = 10.dp2px(context)
                rightMargin = 10.dp2px(context)
            }
//            scaleType = ImageView.ScaleType.CENTER_CROP
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
        }
    }

    fun setHomeBean(bean: HomeBean) {
//        if (bean.isSuccess)
        if (bean.bannerList.isNotEmpty()) bannerView.refreshData(bean.bannerList) else removeHeaderView(bannerView)
        if (bean.girlList.isNotEmpty()) imageView.loadUrl(bean.girlList[0].coverImageUrl) else removeHeaderView(imageView)
        setNewData(bean.articleList)
    }

    fun loadData(list: ArrayList<ArticleListBean>) {

    }

    fun hiddenChange(hidden: Boolean) {
        if (hidden) {
            bannerView.stopLoop()
        } else {
            bannerView.startLoop()
        }
    }


}