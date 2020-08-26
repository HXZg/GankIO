package com.hxz.gankio.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import java.io.InputStream
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @title com.hxz.gankio.utils  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BigView
 * @DATE 2020/6/29  8:44 星期一
 */
class BigView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) :
    View(context,attributeSet,defStyle) , GestureDetector.OnGestureListener {

    private val mRect = Rect()
    private val mOptions = BitmapFactory.Options()
    private val mScroll = Scroller(context)
    private val mGestureDetector = GestureDetector(context,this)

    private var mViewWidth = 0
    private var mViewHeight = 0
    private var bitmapWidth = 0
    private var bitmapHeight = 0
    private var mScale = 0f
    private var bitmap : Bitmap? = null

    private var decoder: BitmapRegionDecoder? = null

    init {
        setOnTouchListener { v, event ->
            mGestureDetector.onTouchEvent(event)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mViewWidth = width
        mViewHeight = height


    }

    public fun setImage(inputStream: InputStream) {
        mOptions.inJustDecodeBounds = true
        BitmapFactory.decodeStream(inputStream,null,mOptions)
        bitmapWidth = mOptions.outWidth
        bitmapHeight = mOptions.outHeight


        mRect.top = 0
        mRect.left = 0
        mScale = mViewWidth / bitmapWidth.toFloat()
        mRect.bottom = (mViewHeight / mScale).toInt()
        mRect.right = bitmapWidth

        mOptions.inMutable = true
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565
        mOptions.inJustDecodeBounds = false

        decoder = BitmapRegionDecoder.newInstance(inputStream, true)

        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mOptions.inBitmap = bitmap
        bitmap = decoder?.decodeRegion(mRect, mOptions)
        val matrix = Matrix()
        matrix.setScale(mScale,mScale)
        canvas?.drawBitmap(bitmap!!,matrix,null)
    }


    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        if (!mScroll.isFinished) {
            mScroll.forceFinished(true)
        }
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        mScroll.fling(0,mRect.top,0,-velocityY.toInt(),0,0,0,
            bitmapHeight - (mViewHeight / mScale).toInt()
        )
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        mRect.offset(0,distanceY.toInt())

        if (mRect.top < 0) {
            mRect.top = 0
            mRect.bottom = mViewHeight / mScale.toInt()
        }

        if (mRect.bottom > bitmapHeight) {
            mRect.bottom = bitmapHeight
            mRect.top = bitmapHeight - mViewHeight / mScale.toInt()
        }
        invalidate()
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroll.isFinished) {
            return
        }
        if (mScroll.computeScrollOffset()) {
            mRect.top = mScroll.currY
            mRect.bottom = mRect.top + (mViewHeight / mScale).toInt()
            invalidate()
        }
    }
}