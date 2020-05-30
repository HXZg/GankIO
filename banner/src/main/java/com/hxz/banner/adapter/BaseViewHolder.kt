package com.hxz.banner.adapter

import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val views = SparseArray<View>()

    fun<T: View> findView(@IdRes res: Int): T {
        var v = views[res]
        if (v == null) {
            v = itemView.findViewById<T>(res)
            views.put(res,v)
        }
        return v as T
    }

    fun setText(res: Int,text: String) {
        findView<TextView>(res).text = text
    }

    fun setImageResource(@IdRes res: Int,@DrawableRes resId: Int) {
        findView<ImageView>(res).setImageResource(resId)
    }

    fun setBackground(res: Int,drawable: Drawable) {
        findView<View>(res).background = drawable
    }
}