package com.hxz.gankio.activity

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.hxz.baseui.view.BaseActivity
import com.hxz.gankio.R
import com.hxz.gankio.utils.DataClearUtils
import com.hxz.gankio.utils.SPUtils
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * @title com.hxz.gankio.activity  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des SettingActivity
 * @DATE 2020/6/15  14:29 星期一
 */
class SettingActivity : BaseActivity() {

    private var mCurrentMode = 0
    private val modeList = arrayListOf(R.string.dark_mode,R.string.dark_no,R.string.dark_follow_system)
    private val modeValueList = arrayListOf(AppCompatDelegate.MODE_NIGHT_YES,AppCompatDelegate.MODE_NIGHT_NO,
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    override fun bindLayout(): Int = R.layout.activity_setting

    override fun initData() {
        setSupportActionBar(tool_bar)
        tool_bar.setNavigationIcon(R.drawable.ic_back_wh)
        tool_bar.setNavigationOnClickListener { finish() }
        showData()
        initEvent()
    }

    private fun initEvent() {
        tv_set_dark.setOnClickListener {
            mCurrentMode = ++mCurrentMode % 3
            SPUtils.setDarkMode(this,modeValueList[mCurrentMode])
            AppCompatDelegate.setDefaultNightMode(modeValueList[mCurrentMode])
            tv_dark_mode.setText(modeList[mCurrentMode])
        }
        tv_clear_cache.setOnClickListener {
            showCacheDialog()
        }
    }

    private fun showData() {
        val modeValue = SPUtils.getDarkMode(this)
        mCurrentMode = modeValueList.indexOf(modeValue)
        if (mCurrentMode in 0..2) {
            tv_dark_mode.setText(modeList[mCurrentMode])
        }
        tv_cache_size.text = DataClearUtils.getTotalCacheSize(this)
    }

    private fun showCacheDialog() {

        AlertDialog.Builder(this)
            .setMessage("确认清除缓存")
            .setNegativeButton("取消") {dialog,which ->
                dialog.dismiss()
            }
            .setPositiveButton("确认") {dialog,which->
                DataClearUtils.clearAllCache(this)
                tv_cache_size.text = DataClearUtils.getTotalCacheSize(this)
            }
            .show()
    }


}