package com.hxz.gankio.repository

/**
 * @title com.hxz.gankio.repository  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des HotRepository
 * @DATE 2020/6/5  14:29 星期五
 */
class HotRepository : BaseRepository() {

    fun getHotList(hot_type: String,category: String) = fire {
        api.getHotList(hot_type,category, HOT_PAGE_COUNT)
    }
}