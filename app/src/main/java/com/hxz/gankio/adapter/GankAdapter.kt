package com.hxz.gankio.adapter

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hxz.basehttp.bean.CategoryTypeBean
import com.hxz.gankio.R
import com.hxz.gankio.fragment.ListFragment
import net.lucode.hackware.magicindicator.buildins.UIUtil
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

class GankMagicAdapter(private val data: ArrayList<CategoryTypeBean> = arrayListOf(),private val click: (position: Int) -> Unit) : CommonNavigatorAdapter() {
    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
        val view = ClipPagerTitleView(context)
        view.text = data[index].title
        view.textColor = context!!.resources.getColor(R.color.colorAccent)
        view.clipColor = Color.WHITE
        view.setOnClickListener{ click(index) }
        return view
    }

    override fun getCount(): Int = data.size

    override fun getIndicator(context: Context?): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        val navigatorHeight =
            context!!.resources.getDimension(R.dimen.bar_height)
        val borderWidth = UIUtil.dip2px(context, 1.0).toFloat()
        val lineHeight = navigatorHeight - 2 * borderWidth
        indicator.lineHeight = lineHeight
        indicator.roundRadius = lineHeight / 2
        indicator.yOffset = borderWidth
        indicator.setColors(context.resources.getColor(R.color.colorAccent))
        return indicator
    }

    fun setNewData(list: ArrayList<CategoryTypeBean>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

}