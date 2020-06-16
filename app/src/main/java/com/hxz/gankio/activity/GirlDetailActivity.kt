package com.hxz.gankio.activity

import android.content.Context
import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.baseui.view.BaseActivity
import com.hxz.gankio.R
import com.hxz.gankio.adapter.GirlDetailAdapter
import com.hxz.gankio.utils.startTActivity
import kotlinx.android.synthetic.main.activity_detail_girl.*

/**
 * @title com.hxz.gankio.activity  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des GirlDetailActivity
 * @DATE 2020/6/12  17:12 星期五
 */
class GirlDetailActivity : BaseActivity() {

    companion object {
        fun startGirlDetail(context: Context,list: ArrayList<ArticleListBean>,page: Int,position: Int) {
            context.startTActivity<GirlDetailActivity>{
                putExtra("girl_list",list)
                putExtra("girl_page",page)
                putExtra("girl_position",position)
            }
        }
    }

    private val mGirlList by lazy { intent?.getParcelableArrayListExtra<ArticleListBean>("girl_list") }
    private val mGirlPage by lazy { intent?.getIntExtra("girl_page",-1) ?: -1 }
    private val mGirlPosition by lazy { intent?.getIntExtra("girl_position",0) ?: 0 }

    private val adapter by lazy { GirlDetailAdapter(this,mGirlList ?: arrayListOf()) }

    private val pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

    override fun bindLayout(): Int = R.layout.activity_detail_girl

    override fun initData() {
        vp2_girl_detail.adapter = adapter
        vp2_girl_detail.registerOnPageChangeCallback(pageChangeListener)
        if (mGirlPosition >= 0) vp2_girl_detail.setCurrentItem(mGirlPosition,false)
    }

    override fun onStop() {
        vp2_girl_detail.unregisterOnPageChangeCallback(pageChangeListener)
        super.onStop()
    }
}