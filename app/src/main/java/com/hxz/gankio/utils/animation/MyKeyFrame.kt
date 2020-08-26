package com.hxz.gankio.utils.animation

/**
 * @title com.hxz.gankio.utils.animation  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des MyKeyFrame
 * @DATE 2020/7/21  9:05 星期二
 */
class MyKeyFrame private constructor(val fraction: Float,val value: Float){

    companion object{
        fun ofFloat(fraction: Float,value: Float) : MyKeyFrame {
            return MyKeyFrame(fraction, value)
        }
    }

}