package com.hxz.gankio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<DATA>(private val data: MutableList<DATA> = arrayListOf(),private @LayoutRes val layout: Int = 0) : RecyclerView.Adapter<BaseRvHolder>() {

    companion object {
        private const val HEAD_VIEW = 0
        private const val FOOT_VIEW = 1
        private const val NORMAL = 2
        private const val EMPTY_VIEW = 3
    }

    var emptyView : View? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    private val headViews = arrayListOf<View>()
    private val footViews = arrayListOf<View>()

    var clickListener : RvClickListener<DATA>? = null

    fun setClickInvoke(action: (position: Int,data: DATA) -> Unit) {
        clickListener = object : RvClickListener<DATA> {
            override fun click(position: Int, data: DATA) {
                action(position,data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvHolder {
        return createHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseRvHolder, position: Int) {
        when(getItemViewType(position)) {
            HEAD_VIEW -> holder.addItemViews(headViews)
            FOOT_VIEW -> holder.addItemViews(footViews)
            EMPTY_VIEW -> {}  // 显示 empty view 不做特殊处理
            else -> {
                holder.itemView.setOnClickListener { clickListener?.click(position,data = data[position - isHead()]) }
                convertViewHolder(holder,data = data[position - isHead()])
            }
        }
    }

    private fun isHead() : Int {
        return if (headViews.isNotEmpty()) 1 else 0
    }

    abstract fun convertViewHolder(holder: BaseRvHolder,data: DATA)

    private fun createHolder(parent: ViewGroup,viewType: Int) : BaseRvHolder {
        val v = when(viewType) {
            NORMAL -> if (layout != 0) LayoutInflater.from(parent.context).inflate(layout,parent,false) else createNormalView(parent, viewType)
            HEAD_VIEW -> getHeadAndFootView(parent.context)
            EMPTY_VIEW -> emptyView!!
            else -> getHeadAndFootView(parent.context)
        }
        return BaseRvHolder(v)
    }

    protected fun createNormalView(parent: ViewGroup,viewType: Int) : View {
        return View(parent.context)
    }

    fun setNewData(list: MutableList<DATA>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun loadDataList(list: MutableList<DATA>) {
        val positionStart = itemCount
        data.addAll(list)
        notifyItemRangeInserted(positionStart,list.size)
    }

    fun addHeaderView(view: View) {
        headViews.add(view)
        notifyItemChanged(0)
    }

    fun removeHeaderView(view: View? = null) {
        val count = headViews.size
        if (view == null) headViews.clear() else headViews.remove(view)
        notifyItemChanged(0)
    }

    fun addFootView(view: View) {
        footViews.add(view)
        notifyItemChanged(itemCount - 1)
    }

    fun removeFootView(view: View? = null) {
        val count = footViews.size
        if (view == null) footViews.clear() else footViews.remove(view)
        notifyItemChanged(itemCount - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> if (headViews.isNotEmpty()) HEAD_VIEW else createItemType(0)
            in 1..getDataCount() -> createItemType(position)
            else -> if (footViews.isNotEmpty()) FOOT_VIEW else createItemType(position)
        }
    }

    private fun getHeadAndFootView(context: Context) : LinearLayout {
        return LinearLayout(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1,-2)
            orientation = LinearLayout.VERTICAL
        }
    }

    /**
     * 重写此方法 实现多type position 实际去掉头部开始
     */
    protected fun createItemType(position: Int) : Int {
        return if (data.isEmpty() && emptyView != null) EMPTY_VIEW else NORMAL
    }

    override fun getItemCount(): Int = getDataCount() + isHeadAndFoot()

    private fun isHeadAndFoot() : Int {
        var count = 0
        if (headViews.isNotEmpty()) ++count
        if (footViews.isNotEmpty()) ++count
        return count
    }

    /**
     * 重写此方法  默认返回数据大小  若数据为空，则显示emptyView
     */
    protected fun getDataCount() = if (data.isEmpty() && emptyView != null) 1 else data.size

    interface RvClickListener<DATA> {
        fun click(position: Int,data: DATA)
    }
}