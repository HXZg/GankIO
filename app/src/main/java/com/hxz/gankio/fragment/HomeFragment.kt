package com.hxz.gankio.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hxz.banner.Banner
import com.hxz.banner.adapter.BaseBannerAdapter
import com.hxz.banner.adapter.BaseViewHolder
import com.hxz.basehttp.bean.BannerBean
import com.hxz.baseui.view.BaseMFragment
import com.hxz.gankio.R
import com.hxz.gankio.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @title com.hxz.gankio.fragment  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des HomeFragment
 * @DATE 2020/5/29  17:34 星期五
 */
class HomeFragment : BaseMFragment<HomeViewModel>() {

    private val adapter = object : BaseBannerAdapter<BannerBean>() {
        override fun createView(parent: ViewGroup, viewType: Int): View {
            return ImageView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(-1,-1)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

        override fun convertHolder(holder: BaseViewHolder, item: BannerBean) {
            Glide.with(holder.itemView.context).applyDefaultRequestOptions(RequestOptions.centerCropTransform()).load(item.image).into(holder.itemView as ImageView)
        }
    }

    override fun createVM(): HomeViewModel = viewModels<HomeViewModel>().value

    override fun bindLayout(): Int = R.layout.fragment_home

    override fun beforeBindLayout(saveInstanceState: Bundle?) {

    }

    override fun initData() {
        viewModel.getBanner()
        initLiveObserve()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i("banner_view","$hidden")
        if (hidden) {
            home_banner.stopLoop()
        }else{
            home_banner.startLoop()
        }
    }

    private fun initLiveObserve() {
        viewModel.homeLive.observe(this, Observer {
            (home_banner as Banner<BannerBean>).apply {
                setAdapter(adapter)
                refreshData(it)
                setCanLoop(true)
                setPageClickListener {
                    Toast.makeText(context,"$it",Toast.LENGTH_SHORT).show()
                }
                setInDecorateColor(Color.BLACK,Color.RED)  // 必须在adapter 之后设置
                setAutoPlay(true)
            }
        })
    }
}