package com.hxz.gankio.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hxz.basehttp.bean.CategoryTypeBean
import com.hxz.gankio.fragment.ListFragment
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView

class GankAdapter(private val category: String,fragment: Fragment,private val data: ArrayList<CategoryTypeBean> = arrayListOf()) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment {
        return ListFragment.newListFragment(category,data[position].type)
    }

    fun setNewDataList(list: MutableList<CategoryTypeBean>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}

class GankMagicAdapter(private val data: ArrayList<CategoryTypeBean> = arrayListOf()) : CommonNavigatorAdapter() {
    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
        val view = ClipPagerTitleView(context)

        return view
    }

    override fun getCount(): Int = data.size

    override fun getIndicator(context: Context?): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        return indicator
    }

    fun setNewData(list: ArrayList<CategoryTypeBean>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

}