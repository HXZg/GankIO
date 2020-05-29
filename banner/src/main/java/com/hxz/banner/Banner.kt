package com.hxz.banner

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.hxz.banner.adapter.BaseBannerAdapter

class Banner @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, style: Int = 0) :
    FrameLayout(context,attributes,style) {

    val mViewPager by lazy { ViewPager2(context) }

    private fun initViewPager() {
        mViewPager.adapter = BaseBannerAdapter()
        mViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        addView(mViewPager)
    }

    private fun init() {



    }
}