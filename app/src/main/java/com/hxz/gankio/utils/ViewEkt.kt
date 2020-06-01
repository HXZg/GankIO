package com.hxz.gankio.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadUrl(url: String) {
    Glide.with(context).applyDefaultRequestOptions(RequestOptions.centerCropTransform()).load(url).into(this)
}