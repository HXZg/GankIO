package com.hxz.banner.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.hxz.banner.Banner
import com.hxz.banner.utils.BannerUtils

abstract class BaseBannerAdapter<T>(data: MutableList<T> = arrayListOf()) : RecyclerView.Adapter<BaseViewHolder>() {

    var dataList : MutableList<T> = data
    private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(createView(parent, viewType))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            BannerUtils.clickListener?.invoke(getRealPosition(position))
        }
        convertHolder(holder,dataList[getRealPosition(position)])
    }

    override fun getItemCount(): Int {
        return if (BannerUtils.isCanLoop) BannerUtils.MAX_VALUE_SIZE else dataList.size
    }

    fun setData(list: MutableList<T>) {
        dataList = list
        notifyDataSetChanged()
    }

    fun addData(t: T) {
        dataList.add(t)
        notifyItemChanged(dataList.size)
    }

    fun getRealPosition(position: Int) : Int {
       return position % dataList.size
    }

    abstract fun createView(parent: ViewGroup,viewType: Int) : View

    abstract fun convertHolder(holder: BaseViewHolder,item: T)
}