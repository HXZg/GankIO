package com.hxz.gankio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @title com.hxz.gankio.viewmodel  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des ListFactory
 * @DATE 2020/6/5  17:17 星期五
 */
class ListFactory(val category: String,val type: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(String::class.java,String::class.java).newInstance(category,type)
    }
}