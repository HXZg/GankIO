package com.hxz.gankio.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hxz.basehttp.bean.ArticleListBean
import com.hxz.basehttp.bean.BannerBean
import com.hxz.basehttp.bean.BaseResponseBean
import com.hxz.basehttp.bean.CategoryTypeBean
import com.hxz.baseui.viewmodel.BaseViewModel
import com.hxz.gankio.bean.HomeBean
import com.hxz.gankio.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    val homeLive = MutableLiveData<HomeBean>()

    private val repository = BaseRepository()

    private val page_count = 10

    fun getHomeData() {
        viewModelScope.launch(Dispatchers.IO) {
            val girl =
                async { repository.cacheException { repository.api.getCategoryType("Girl") } }
            val list =
                async { repository.cacheException {
                    repository.api.getArticleList(BaseRepository.CATEGORY_ALL,BaseRepository.CATEGORY_ALL,1,page_count) } }
            val banner =
                async { repository.cacheException { repository.api.getBanners() } }
            homeData(banner.await(),girl.await(),list.await())
        }
    }

    fun loadData(page: Int) {
        viewModelScope.launch {
            val list = async {
                repository.cacheException { repository.api.getArticleList(BaseRepository.CATEGORY_ALL,BaseRepository.CATEGORY_ALL,page,page_count) }
            }

        }
    }

    private fun homeData(bean: BaseResponseBean<ArrayList<BannerBean>>,girl: BaseResponseBean<ArrayList<CategoryTypeBean>>,list: BaseResponseBean<ArrayList<ArticleListBean>>) {
        /*val bean = HomeBean.getHomeBean(bean,girl,list){_,_,_ ->
            // 如果有数据获取出错，回调这里，处理
            other(0)
        }*/
//        if (bean.isSuccess)  可以通过这个判断是否通知显示数据
        homeLive.postValue(HomeBean.getHomeBean(bean,girl,list))
    }
}