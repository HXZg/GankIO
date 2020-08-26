package com.hxz.gankio.utils.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AnimationSet
import java.lang.ref.WeakReference

/**
 * @title com.hxz.gankio.utils.animation  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des MyObjectAnimate
 * @DATE 2020/7/21  9:01 星期二
 */
class MyObjectAnimate private constructor(view: View,poName: String,vararg float: Float){

    var duration : Long = 0L
    private var holder: Holder = Holder.ofFloat(poName, *float)
    private val weakReference = WeakReference<View>(view)
    private var index = 0

    companion object{
        /**
         * view: 反射调用
         * poName: 拼接获取对应的setter方法
         * float: 设置关键帧
         */
        fun ofFloat(view: View,poName: String,vararg float: Float) : MyObjectAnimate {
            ObjectAnimator.ofFloat(view,poName,*float).setDuration(3000L).start()
            return MyObjectAnimate(view,poName, *float)
        }
    }

    fun start() {
        holder.setUpSetter()
        //  注册 VSYNC 信号监听
    }

    // VSYNC 信号  16ms 调用一次
    fun doFrameAnimation(currentTime: Long) {
        val total = duration / 16
        var fraction = (index++) / total.toFloat()
        // 插值器  重新计算 fraction

        if (index >= total) {
            index = 0 // repeat

//            weakReference.clear()
            // 移除监听
        }
        holder.setAnimateValue(weakReference.get(),fraction)
    }




}