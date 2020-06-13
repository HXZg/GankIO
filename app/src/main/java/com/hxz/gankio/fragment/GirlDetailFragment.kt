package com.hxz.gankio.fragment

import android.os.Bundle
import com.hxz.baseui.view.BaseFragment
import com.hxz.gankio.R
import com.hxz.gankio.utils.loadUrl
import kotlinx.android.synthetic.main.fragment_girl_detail.*

/**
 * @title com.hxz.gankio.fragment  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des GirlDetailFragment
 * @DATE 2020/6/12  17:33 星期五
 */
class GirlDetailFragment : BaseFragment() {

    companion object {
        private const val GIRL_URL = "girl_url"
        private const val GIRL_TYPE = "girl_type"
        private const val GIRL_DESC = "girl_desc"
        fun newGirlFragment(url: String,type: String,desc: String) : GirlDetailFragment{
            return GirlDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(GIRL_URL,url)
                    putString(GIRL_TYPE,type)
                    putString(GIRL_DESC,desc)
                }
            }
        }
    }

    private val mUrl by lazy { arguments?.getString(GIRL_URL) }
    private val mType by lazy { arguments?.getString(GIRL_TYPE) }
    private val mDesc by lazy { arguments?.getString(GIRL_DESC) }

    override fun bindLayout(): Int = R.layout.fragment_girl_detail

    override fun initData() {
        if (!mUrl.isNullOrEmpty()) iv_girl_big.loadUrl(mUrl ?: "")
    }
}