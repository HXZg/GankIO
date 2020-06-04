package com.hxz.gankio.adapter

import android.widget.ImageView
import com.hxz.basehttp.bean.CategoryTypeBean
import com.hxz.gankio.R
import com.hxz.gankio.utils.loadUrl

/**
 * @title com.hxz.gankio.adapter  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des CategoryAdapter
 * @DATE 2020/6/4  10:44 星期四
 */
class CategoryAdapter : BaseRvAdapter<CategoryTypeBean>(layout = R.layout.item_article) {

    override fun convertViewHolder(holder: BaseRvHolder, data: CategoryTypeBean) {
        holder.apply {
            findView<ImageView>(R.id.iv_img).loadUrl(data.coverImageUrl,isError = false,gifOne = false)
            setText(R.id.tv_title,t = data.title)
            setText(R.id.tv_desc,t = data.desc)
        }
    }
}