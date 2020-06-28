package com.hxz.gankio.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import androidx.constraintlayout.helper.widget.Layer
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @title com.hxz.gankio.utils  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des SplashLayer
 * @DATE 2020/6/28  15:55 星期日
 */
class SplashLayer @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : Layer(context,attributeSet,defStyle) {


    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)

        val centerPoint = PointF(((left + right) / 2).toFloat(), ((top + bottom) / 2).toFloat())

        val animator = ValueAnimator.ofFloat(0f,1f).setDuration(2000L)
        animator.addUpdateListener {
            update(centerPoint,it.animatedFraction,container)
        }
        animator.start()
    }

    private fun update(center: PointF,fraction: Float,container: ConstraintLayout?) {
        getViews(container).forEach {
            val viewCenterX = (it.left + it.right) / 2
            val viewCenterY = (it.top + it.bottom) / 2

            val startTranslationX = if (viewCenterX < center.x) -1000f else 1000f  // 偏右边 则 从最右边飞入
            val startTranslationY = if (viewCenterY < center.y) -1000f else 1000f  // 偏下边 则 从最下边飞入

            it.translationX = (1 - fraction) * startTranslationX   // 0-1 之间的变化速率 实际偏移量
            it.translationY = (1 - fraction) * startTranslationY

            it.rotation = (1 - fraction) * 360  // 360度旋转
        }
    }

}