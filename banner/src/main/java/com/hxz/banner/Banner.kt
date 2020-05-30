package com.hxz.banner

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.hxz.banner.adapter.BaseBannerAdapter
import com.hxz.banner.utils.BannerUtils
import com.hxz.banner.utils.PageClickListener

class Banner<T> @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, style: Int = 0) :
    FrameLayout(context,attributes,style) {

    private val pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            pageChangeCallback?.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            pageChangeCallback?.onPageScrolled(adapter.getRealPosition(position),positionOffset,positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val realPosition = adapter.getRealPosition(position)
            if (position == 0 || position == BannerUtils.MAX_VALUE_SIZE - 1) {
                resetCurrentItem(realPosition)
            }
            pageChangeCallback?.onPageSelected(realPosition)
        }
    }

    private val loopRun = Runnable {
        var item = mViewPager.currentItem
        if (++item < adapter.itemCount) {
            mViewPager.setCurrentItem(item,true)
            delayPostLoop()
        }
    }

    val mViewPager by lazy { ViewPager2(context) }

    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    private lateinit var adapter : BaseBannerAdapter<T>

    private var AUTO_PLAY_TIME = 2000L

    private var isStartLoop = false

    init {
        initViewPager()
        initDecoration()
    }

    private fun initViewPager() {
        mViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val params = LayoutParams(-1,-1)  // 完全填充父布局
        addView(mViewPager,params)
    }

    private fun initDecoration() {  // 指示器
        //TODO  指示器  与  transform  实现
        val params = LayoutParams(-2,-2)  // 自适应布局
    }

    fun setPageClickListener(listener: PageClickListener) {
        BannerUtils.clickListener = listener
    }

    fun setPageChangeCallback(listener: ViewPager2.OnPageChangeCallback) {
        this.pageChangeCallback = listener
    }

    fun setAdapter(adapter: BaseBannerAdapter<T>) {
        this.adapter = adapter
        mViewPager.adapter = adapter
        resetCurrentItem(0)
        mViewPager.unregisterOnPageChangeCallback(pageChangeListener)
        mViewPager.registerOnPageChangeCallback(pageChangeListener)
    }

    fun refreshData(list: MutableList<T>) {
        if (::adapter.isInitialized) {
            adapter.setData(list)
            resetCurrentItem(0)
            mViewPager.unregisterOnPageChangeCallback(pageChangeListener)
            mViewPager.registerOnPageChangeCallback(pageChangeListener)
        }
    }

    fun setCanLoop(loop: Boolean) {
        BannerUtils.isCanLoop = loop
        if (::adapter.isInitialized) adapter.notifyDataSetChanged()
    }

    fun setAutoPlay(auto: Boolean) {
        BannerUtils.isAutoPlay = auto
        startLoop()
    }

    private fun startLoop(time: Long = AUTO_PLAY_TIME) {
        if (!isStartLoop && BannerUtils.isAutoPlay && ::adapter.isInitialized) {
            isStartLoop = true
            AUTO_PLAY_TIME = time
            delayPostLoop()
        }
    }

    private fun delayPostLoop() {
        postDelayed(loopRun,AUTO_PLAY_TIME)
    }

    fun stopLoop() {
        if (isStartLoop) {
            isStartLoop = false
            removeCallbacks(loopRun)
        }
    }

    override fun onAttachedToWindow() {
        startLoop()
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        stopLoop()
        super.onDetachedFromWindow()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                isStartLoop = true
                stopLoop()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                isStartLoop = false
                startLoop()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun resetCurrentItem(item: Int) {
        mViewPager.setCurrentItem(BannerUtils.getResetItem(item,adapter.dataList.size),false)
    }

}