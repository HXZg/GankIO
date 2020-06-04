package com.hxz.gankio.fragment

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.hxz.basehttp.bean.CategoryTypeBean
import com.hxz.baseui.view.BaseFragment
import com.hxz.gankio.R
import com.hxz.gankio.adapter.GankAdapter
import com.hxz.gankio.adapter.GankMagicAdapter
import com.hxz.gankio.repository.BaseRepository
import com.hxz.gankio.viewmodel.GankViewModel
import kotlinx.android.synthetic.main.fragment_gank.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView

class GankFragment : BaseFragment() {

    private val vpAdapter by lazy { GankAdapter(BaseRepository.CATEGORY_GANK,this) }
    private val magicAdapter by lazy { GankMagicAdapter() }

    private val viewModel by lazy { viewModels<GankViewModel>().value }

    override fun bindLayout(): Int = R.layout.fragment_gank

    override fun initData() {
        vp2_gank.adapter = vpAdapter
        initMagicIndicator()
        initObserver()
    }

    private fun initObserver() {
        viewModel.getGankType().observe(this, Observer {
            val page = viewModel.getGankPage().value
            setPageData(it.data ?: arrayListOf(), page ?: 0)
        })
    }

    private fun setPageData(data: ArrayList<CategoryTypeBean>,page: Int) {
        magicAdapter.setNewData(data)
        vpAdapter.setNewDataList(data)
        vp2_gank.setCurrentItem(page,false)
    }

    private fun initMagicIndicator() {
        mgi_gank.navigator = CommonNavigator(requireContext()).apply {
            adapter = magicAdapter
        }
        vp2_gank.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                mgi_gank.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                mgi_gank.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mgi_gank.onPageSelected(position)
                viewModel.savePage(position)
            }
        })
    }

}