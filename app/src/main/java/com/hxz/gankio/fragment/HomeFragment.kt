package com.hxz.gankio.fragment

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.hxz.banner.Banner
import com.hxz.banner.adapter.BaseBannerAdapter
import com.hxz.baseui.view.BaseFragment
import com.hxz.gankio.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @title com.hxz.gankio.fragment  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des HomeFragment
 * @DATE 2020/5/29  17:34 星期五
 */
class HomeFragment : BaseFragment() {

    private val adapter = object : BaseBannerAdapter<Int>(arrayListOf(R.drawable.family_off,R.drawable.family_on)) {
        override fun createView(parent: ViewGroup, viewType: Int): View {
            return ImageView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(-1,-1)
            }
        }

        override fun convertHolder(view: View, item: Int) {
            (view as ImageView).setImageResource(item)
        }
    }

    override fun bindLayout(): Int = R.layout.fragment_home

    override fun beforeBindLayout(saveInstanceState: Bundle?) {

    }

    override fun initData() {
        (home_banner as Banner<Int>).apply {
            setCanLoop(true)
            setAdapter(adapter)
            setPageClickListener {
                Toast.makeText(context,"$it",Toast.LENGTH_SHORT).show()
            }
            setAutoPlay(true)
        }
    }
}