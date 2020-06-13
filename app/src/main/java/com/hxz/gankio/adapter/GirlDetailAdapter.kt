package com.hxz.gankio.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.gankio.fragment.GirlDetailFragment

/**
 * @title com.hxz.gankio.adapter  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des GirlDetailAdapter
 * @DATE 2020/6/13  9:31 星期六
 */
class GirlDetailAdapter(activity: FragmentActivity,val list: MutableList<ArticleListBean>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        val bean = list[position]
        return GirlDetailFragment.newGirlFragment(bean.url,bean.title,bean.desc)
    }
}