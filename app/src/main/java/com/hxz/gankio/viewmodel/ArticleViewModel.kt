package com.hxz.gankio.viewmodel

import androidx.lifecycle.viewModelScope
import com.hxz.baseui.viewmodel.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ArticleViewModel : BaseViewModel() {

    fun getArticleData() {
        viewModelScope.launch {
            val list = async {  }
        }
    }
}