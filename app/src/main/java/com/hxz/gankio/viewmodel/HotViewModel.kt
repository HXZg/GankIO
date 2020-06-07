package com.hxz.gankio.viewmodel

import androidx.lifecycle.*
import com.hxz.gankio.repository.HotRepository

/**
 * @title com.hxz.gankio.viewmodel  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des HotViewModel
 * @DATE 2020/6/5  14:31 星期五
 */
class HotViewModel(private val handle: SavedStateHandle) : ViewModel() {

    companion object{
        private const val HOT_TYPE = "hot_type"
        private const val HOT_CATEGORY = "hot_category"
    }

    private val repository = HotRepository()

    private val categoryLive = handle.getLiveData<String>(HOT_CATEGORY)

    val hotListLive = Transformations.switchMap(categoryLive) {
        getHotList(getHotType() ?: "views",it)
    }
    /*val titleLive = Transformations.map(categoryLive) {
        val start = when(getHotType() ?: "views") {
            "views" -> "浏览数"
            "comments" -> "评论数"
            else -> "点赞数"
        }
        val end = when(it) {
            BaseRepository.CATEGORY_GANK -> "干货"
            BaseRepository.CATEGORY_GIRL -> "妹纸"
            else -> "文章"
        }
        "本周${start}最多的$end"
    }*/

    private fun getHotList(hot_type: String,category: String) =
        repository.getHotList(hot_type,category)

    fun setHotType(type: String) = handle.set(HOT_TYPE,type)

    fun setHotCategory(category: String) = handle.set(HOT_CATEGORY,category)

    fun getHotType() = handle.get<String>(HOT_TYPE)

    fun getHotCategory() = handle.get<String>(HOT_CATEGORY)

}