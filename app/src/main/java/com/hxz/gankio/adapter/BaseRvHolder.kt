package com.hxz.gankio.adapter

import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.recyclerview.widget.RecyclerView

class BaseRvHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val views = SparseArray<View>()

    fun<T: View> findView(@IdRes res: Int): T {
        var view = views[res]
        if (view == null) {
            view = itemView.findViewById<T>(res)
            views.put(res,view)
        }
        return view as T
    }

    fun setText(@IdRes id: Int,@StringRes s : Int = 0,t: String? = null) {
        val text = t ?: itemView.context.getString(s)
        findView<TextView>(id).text = text
    }

    fun setTextColor(@IdRes id: Int,@ColorRes color: Int = 0,@ColorInt c: Int? = null) {
        val tv = findView<TextView>(id)
        val ci = c ?: tv.context.resources.getColor(color)
        tv.setTextColor(ci)
    }

    fun setTextSize(@IdRes id: Int,size: Float) {
        findView<TextView>(id).textSize = size
    }

    fun setImageResource(@IdRes id: Int,@DrawableRes drawable: Int) {
        findView<ImageView>(id).setImageResource(drawable)
    }

    fun setImageDrawable(@IdRes id: Int,drawable: Drawable) {
        findView<ImageView>(id).setImageDrawable(drawable)
    }

    fun setBackgroundDrawable(@IdRes id: Int,drawable: Drawable) {
        findView<View>(id).background = drawable
    }

    fun addItemViews(views: List<View>) {
        if (itemView is ViewGroup) {
            itemView.removeAllViews()
            views.forEach { itemView.addView(it) }
        }
    }
}