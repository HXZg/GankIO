package com.hxz.gankio.utils.animation

import android.view.View
import java.lang.reflect.Method


/**
 * @title com.hxz.gankio.utils.animation  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des Holder
 * @DATE 2020/7/21  9:19 星期二
 */
class Holder private constructor(val poName: String,vararg float: Float){

    private lateinit var keyFrameSet : MyKeyFrameSet
    private lateinit var setter: Method

    init {
        initKeyFrameSet(*float)

    }

    companion object{
        fun ofFloat(poName: String,vararg float: Float) : Holder {
            return Holder(poName, *float)
        }
    }

    fun initKeyFrameSet(vararg float: Float) {
        keyFrameSet = MyKeyFrameSet.ofFloat(*float)
    }

    fun setUpSetter() {
        val first = Character.toUpperCase(poName[0])
        val then = poName.substring(1)
        val methodName = "set$first$then"
        val method = View::class.java.getMethod(methodName, Float::class.java)
        setter = method
    }

    fun setAnimateValue(target: View?,fraction: Float) {
        val value = keyFrameSet.getValue(fraction)
        setter.invoke(target,value)
    }
}