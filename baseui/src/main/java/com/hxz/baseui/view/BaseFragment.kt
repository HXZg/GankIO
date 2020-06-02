package com.hxz.baseui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hxz.baseui.base.IBaseView

/**
 * @title com.hxz.baseui.view  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des BaseFragment
 * @DATE 2020/5/29  9:55 星期五
 */
abstract class BaseFragment : Fragment(),IBaseView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        beforeBindLayout(arguments)
        val res = bindLayout()
        return if (res > 0) layoutInflater.inflate(res,null,false)
            else super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun beforeBindLayout(saveInstanceState: Bundle?) {

    }
}