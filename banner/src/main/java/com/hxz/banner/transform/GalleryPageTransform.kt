package com.hxz.banner.transform

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

class GalleryPageTransform : ViewPager2.PageTransformer {
    private val MIN_SCALE = 0.9f

    override fun transformPage(page: View, position: Float) {
        val offset = position * -10
        Log.i("banner_view","$position  $offset")
//        page.translationX = offset
        val scale = max(MIN_SCALE,1- abs(position))
        val rotate = 10 * abs(position)
        when{
            position <= -1 -> {
                page.apply {
                    scaleX = MIN_SCALE
                    scaleY = MIN_SCALE
//                    rotationY = rotate
                }
            }
            position < 0 -> {
                page.apply {
                    scaleX = scale
                    scaleY = scale
//                    rotationY = rotate
                }
            }
            position < 1 -> {
                page.apply {
                    scaleX = scale
                    scaleY = scale
//                    rotationY = -rotate
                }
            }
            else -> {
                page.apply {
                    scaleX = scale
                    scaleY = scale
//                    rotationY = -rotate
                }
            }
        }
    }
}