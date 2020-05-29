package com.hxz.baseui.widght

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog

/**
 * @title com.tijio.baseui.widght  PgyTestApp
 * @author xian_zhong  admin
 * @version 1.0
 * @Des LoadingDialog
 * @DATE 2020/5/27  18:07 星期三
 */
class LoadingDialog(context: Context) : AppCompatDialog(context) {

    private lateinit var pb : ProgressBar
    private lateinit var tvTitle : TextView
    private lateinit var mCreateView : LinearLayout

    var mBgColor = Color.WHITE
    var mBgRadius = 30f
    var mBgDrawable : Drawable? = null

    var mBgWidth = 0
    var mBgHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDialogTheme()
        val v = createView()
        setContentView(v,getLayoutParams())
    }

    private fun createView() : View {
        mCreateView = LinearLayout(context)
        mCreateView.orientation = LinearLayout.VERTICAL
        mCreateView.setPadding(10,10,10,10)
        pb = ProgressBar(context)
        tvTitle = TextView(context)
        mCreateView.addView(pb,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        })
        mCreateView.addView(tvTitle,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            topMargin = 10
        })
        return mCreateView
    }

    private fun getLayoutParams() : ViewGroup.LayoutParams {
        val wp = ViewGroup.LayoutParams.WRAP_CONTENT
        return ViewGroup.LayoutParams(wp,wp)
    }

    /** set dialog theme(设置对话框主题)  */
    private fun setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE) // android:windowNoTitle
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // android:windowBackground
        window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // android:backgroundDimEnabled默认是true的
    }

    fun setMessage(text: String) {
        tvTitle.text = text
    }

    fun setProgressDrawable(drawable: Drawable) {
        pb.progressDrawable = drawable
    }

    fun setTextColor(color: Int) {
        tvTitle.setTextColor(color)
    }

    fun setTextSize(size: Float) {
        tvTitle.textSize = size
    }

    fun setBgColorAndRadius(color: Int,radius: Float) {
        mBgColor = color
        mBgRadius = radius
    }

    private fun setDefaultBg() {
        if (mBgDrawable != null) {
            mCreateView.background = mBgDrawable
            return
        }
        val bg = GradientDrawable()
        bg.cornerRadius = mBgRadius
        bg.setColor(mBgColor)
        mCreateView.background = bg
    }

    fun setUiBeforeShow() {
        setDefaultBg()
        mCreateView.layoutParams.apply {
            if (mBgWidth > 0) width = mBgWidth
            if (mBgHeight > 0) height = mBgHeight
        }
    }

    override fun show() {
        setUiBeforeShow()
        super.show()
    }


}