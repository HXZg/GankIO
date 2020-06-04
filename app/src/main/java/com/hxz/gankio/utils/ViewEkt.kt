package com.hxz.gankio.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.hxz.gankio.R

fun ImageView.loadUrl(url: String,isError: Boolean = true,centerCrop: Boolean = true,gifOne: Boolean = true,radius: Int = 0) {
    Glide.with(context).load(url).apply {
        if (gifOne) {
            listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (resource is GifDrawable) {
                        resource.setLoopCount(1)   // 只加载一次gif
                    }
                    return false
                }
            })
        }
        val options = RequestOptions()
        if (centerCrop) options.centerCrop()
        if (isError) options.placeholder(R.drawable.no_photo).error(R.drawable.no_photo)
        if (radius > 0) options.transform(RoundedCorners(radius.dp2px(context)))
        apply(options)
    }.into(this)
}