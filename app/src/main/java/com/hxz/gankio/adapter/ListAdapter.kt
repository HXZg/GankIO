package com.hxz.gankio.adapter

import android.widget.ImageView
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.gankio.R
import com.hxz.gankio.utils.loadUrl

class ListAdapter : BaseRvAdapter<ArticleListBean>(layout = R.layout.item_home) {

    override fun convertViewHolder(holder: BaseRvHolder, data: ArticleListBean) {
        holder.apply {
            if (data.images.isNotEmpty()) holder.findView<ImageView>(R.id.iv_img).loadUrl(data.images[0])
            setText(R.id.tv_title,t = data.title)
            setText(R.id.tv_desc,t = data.desc)
            setText(R.id.tv_time,t = data.publishedAt)
            setText(R.id.tv_auto_type,t = "${data.type}  ${data.author}")
        }
    }
}