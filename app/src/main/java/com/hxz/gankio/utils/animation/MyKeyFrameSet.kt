package com.hxz.gankio.utils.animation

import android.animation.FloatEvaluator
import java.lang.reflect.Method

/**
 * @title com.hxz.gankio.utils.animation  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des MyKeyFrameSet
 * @DATE 2020/7/21  9:05 星期二
 */
class MyKeyFrameSet private constructor(vararg float: Float){

    private val mFirstFrame = MyKeyFrame.ofFloat(0f,float[0])
    private val keyFrames = arrayListOf<MyKeyFrame>()

    init {
        keyFrames.add(0,mFirstFrame)
        for (i in 1 until float.size) {
            keyFrames.add(i, MyKeyFrame.ofFloat(i / (float.size - 1).toFloat(),float[i]))
        }
    }

    // 估值器  计算实际动画移动的值
    fun getValue(fraction: Float) : Float {
        var preFrame = mFirstFrame
        for (i in 1 until keyFrames.size) {
            if (fraction < keyFrames[i].fraction) {
                val evaluate =
                    FloatEvaluator().evaluate(fraction, preFrame.value, keyFrames[i].value)
                return evaluate
            }
            preFrame = keyFrames[i]
        }
        return 0f
    }

    companion object{
        fun ofFloat(vararg float: Float) : MyKeyFrameSet {
            return MyKeyFrameSet(*float)
        }
    }

}