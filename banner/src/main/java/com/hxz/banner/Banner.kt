package com.hxz.banner

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.view.marginLeft
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
            if (inDecorateViews.isNotEmpty()) {
                inDecorateViews[currentPosition].isSelected = false
                inDecorateViews[realPosition].isSelected = true
            }
            currentPosition = realPosition
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

    private val inDecorateViews = arrayListOf<View>()

    private var currentPosition = 0

    init {
        initViewPager()
    }

    private fun initViewPager() {
        mViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val params = LayoutParams(-1,-1)  // 完全填充父布局
        addView(mViewPager,params)
    }

    /**
     * @param orientation 0: HORIZONTAL 1: VERTICAL
     * @param gravity  Gravity.LEFT/RIGHT/BOTTOM/TOP
     */
    private fun initInDecoration(orientation: Int,gravity: Int,drawables: Array<Drawable>) {  // 指示器
        //TODO  指示器  与  transform  实现
        val inLinear = LinearLayout(context)
        inLinear.orientation = orientation
        val params = LayoutParams(-2,-2).apply {
            this.gravity = gravity or Gravity.CENTER
            when(gravity) {
                Gravity.TOP -> topMargin = 10
                Gravity.LEFT -> leftMargin = 10
                Gravity.RIGHT -> rightMargin = 10
                else -> bottomMargin = 10
            }
        } // 自适应布局
        addInDecorateIv(inLinear,drawables)
        inDecorateViews[currentPosition].isSelected = true
        addView(inLinear,params)
    }

    private fun addInDecorateIv(ll: LinearLayout,drawable: Array<Drawable>) {
        drawable.forEach {
            val iv = ImageView(context)
            iv.background = it
            val params = LinearLayout.LayoutParams(-2,-2).apply {
                leftMargin = 20
            }
            inDecorateViews.add(iv)
            ll.addView(iv,params)
        }
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
        initViewPagerChange()
    }

    fun refreshData(list: MutableList<T>) {
        if (::adapter.isInitialized) {
            adapter.setData(list)
            initViewPagerChange()
        }
    }

    private fun initViewPagerChange() {
        resetCurrentItem(0)
        mViewPager.unregisterOnPageChangeCallback(pageChangeListener)
        mViewPager.registerOnPageChangeCallback(pageChangeListener)
    }

    fun setInDecorate(orientation: Int = LinearLayout.HORIZONTAL,gravity: Int = Gravity.BOTTOM,drawables: Array<Drawable>) {
        initInDecoration(orientation,gravity,drawables)
    }

    fun setInDecorate(@DrawableRes unSelect: Int,@DrawableRes select: Int) {
        val drawables = Array<Drawable>(getListSize()){BannerUtils.createStateListDrawable(resources.getDrawable(unSelect),resources.getDrawable(select))}
        setInDecorate(drawables = drawables)
    }

    fun setInDecorateColor(unSelectColor: Int,selectColor: Int) {
        val drawables = Array(getListSize()){BannerUtils.createColorListDrawable(unSelectColor,selectColor)}
        setInDecorate(drawables = drawables)
    }

    fun setInDecorate(@DrawableRes stateDrawable: Int) {
        val drawables = Array<Drawable>(getListSize()){resources.getDrawable(stateDrawable)}
        setInDecorate(drawables = drawables)
    }

    fun setPagerTransform(transform: ViewPager2.PageTransformer) {
        mViewPager.setPageTransformer(transform)
    }

    private fun getListSize() : Int {
        return if (::adapter.isInitialized) adapter.dataList.size else 0
    }

    fun setCanLoop(loop: Boolean) {
        BannerUtils.isCanLoop = loop
        if (::adapter.isInitialized) adapter.notifyDataSetChanged()
    }

    fun setAutoPlay(auto: Boolean) {
        BannerUtils.isAutoPlay = auto
        startLoop()
    }

    fun startLoop(time: Long = AUTO_PLAY_TIME) {
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