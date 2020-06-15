package com.hxz.gankio.activity

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxz.basehttp.bean.Comments
import com.hxz.baseui.util.LogUtils
import com.hxz.baseui.view.BaseMActivity
import com.hxz.baseui.view.WebViewActivity
import com.hxz.baseui.widght.LoadingDialog
import com.hxz.gankio.R
import com.hxz.gankio.adapter.BaseRvAdapter
import com.hxz.gankio.adapter.BaseRvHolder
import com.hxz.gankio.bean.ArticleDetailComments
import com.hxz.gankio.utils.loadUrl
import com.hxz.gankio.utils.setDarkColor
import com.hxz.gankio.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseMActivity<DetailViewModel>() {

    companion object {
        private const val ARTICLE_ID = "article_id"
        fun startDetail(context: Context,id: String) {
            context.startActivity(Intent(context,DetailActivity::class.java).apply {
                putExtra(ARTICLE_ID,id)
            })
        }
    }

    private val id by lazy { intent?.getStringExtra(ARTICLE_ID) ?: "" }

    private val commentAdapter = object : BaseRvAdapter<Comments>(layout = R.layout.item_comments) {
        override fun convertViewHolder(holder: BaseRvHolder, data: Comments) {
            holder.apply {
                setText(R.id.tv_comment_name,t = data.userName)
                setText(R.id.tv_comment_time,t = data.createdAt)
                setText(R.id.tv_comment_content,t = data.comment)
                findView<ImageView>(R.id.iv_comment_img).loadUrl(data.headUrl)
            }
        }
    }

    override fun bindLayout(): Int = R.layout.activity_detail

    override fun createVM(): DetailViewModel {
        return viewModels<DetailViewModel>().value
    }

    override fun initData() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_wh)
        toolbar.setNavigationOnClickListener { finish() }
        rv_detail_comments.layoutManager = LinearLayoutManager(this)
        rv_detail_comments.adapter = commentAdapter

        viewModel.detailCommentsLive.observe(this, Observer {
            if (it.data != null) showDetailData(it.data!!)
        })

        viewModel.getDetail(id)
    }

    private fun showDetailData(bean: ArticleDetailComments) {
        LogUtils.i(bean.detail)
        dismissDialog()
//        supportActionBar?.title = bean.detail?.title
        val img = bean.detail?.images?.get(0)
        if (!img.isNullOrEmpty()) iv_detail_img.loadUrl(img)
        tv_detail_type.text = bean.detail?.type
        tv_detail_title.text = bean.detail?.title
        tv_detail_author.text = "${bean.detail?.author}  ${bean.detail?.publishedat}"
        tv_detail_views.text = "浏览:${bean.detail?.views} 点赞:${bean.detail?.likecounts}"
        tv_detail_desc.text = bean.detail?.desc
        tv_detail_comments.text = "评论:${bean.comments.size}"
        tv_detail_address.text = "项目地址：${bean.detail?.url}"
        commentAdapter.setNewData(bean.comments)
//        val content = if (bean.detail?.content.isNullOrEmpty()) bean.detail?.markdown else bean.detail?.content
        val content = bean.detail?.content
        wv_content.loadData(content, "text/html", "UTF-8")
        wv_content.setBackgroundColor(resources.getColor(R.color.bg_color))

        tv_detail_address.setOnClickListener {
            WebViewActivity.startWebView(this,bean.detail?.url ?: "")
        }
    }

    override fun LoadingDialog.initLoading() {
        setDarkColor()
    }
}