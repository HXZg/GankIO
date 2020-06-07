package com.hxz.gankio.activity

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.hxz.baseui.view.BaseMActivity
import com.hxz.gankio.R
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

    override fun bindLayout(): Int = R.layout.activity_detail

    override fun createVM(): DetailViewModel {
        return viewModels<DetailViewModel>().value
    }

    override fun initData() {
        viewModel.getDetail(id)

        viewModel.detailCommentsLive.observe(this, Observer {

        })
    }
}