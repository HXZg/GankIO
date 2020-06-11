package com.hxz.gankio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.gankio.R
import com.hxz.gankio.utils.loadUrl

/**
 * @title com.hxz.gankio.adapter  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BaseGankAdapter
 * @DATE 2020/6/8  15:54 星期一
 */

fun RecyclerView.setGankManager(gankAdapter: BaseGankAdapter = BaseGankAdapter()) {
    val manager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
    layoutManager = manager
    adapter = gankAdapter

    manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

        override fun getSpanSize(position: Int): Int {
            return if (gankAdapter.getItemViewType(position) != 4) 2 else 1
        }

    }
}

open class BaseGankAdapter : BaseRvAdapter<ArticleListBean>() {

    override fun convertViewHolder(holder: BaseRvHolder, data: ArticleListBean) {
        holder.apply {
            if (getItemViewType(holder.absoluteAdapterPosition) == 4) {
                if (data.images.isNotEmpty()) findView<ImageView>(R.id.iv_girl_img).loadUrl(data.images[0])
                setText(R.id.tv_girl_desc,t = data.desc)
                return@apply
            }
            if (data.images.isNotEmpty()) holder.findView<ImageView>(R.id.iv_img).loadUrl(data.images[0])
            setText(R.id.tv_title,t = data.title)
            setText(R.id.tv_desc,t = data.desc)
            setText(R.id.tv_time,t = data.publishedAt)
            setText(R.id.tv_auto_type,t = "${data.type}  ${data.author}")
        }
    }

    override fun createNormalView(parent: ViewGroup, viewType: Int): View {
        return if (viewType == 4)
            LayoutInflater.from(parent.context).inflate(R.layout.item_girl,parent,false)
        else
            LayoutInflater.from(parent.context).inflate(R.layout.item_home,parent,false)
    }

    override fun createItemType(position: Int): Int {
        val type = super.createItemType(position)
        return if (type == 3) 3 else {
            if (getData(position - isHead()).type == "Girl") 4 else 5  // head 占 1
        }
    }
}