package com.hxz.basehttp.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @title com.hxz.basehttp.bean  GankIO
 * @author xian_zhong  admin
 * @version 1.0
 * @Des GankBean
 * @DATE 2020/5/29  14:31 星期五
 */

data class BannerBean(val image: String,val title: String,val url: String)

data class CategoryTypeBean(val _id: String,val coverImageUrl: String,val desc: String,val title: String,val type: String)

// 文章列表
data class ArticleListBean(val _id: String,val author: String,val category: String,val createdAt: String,val desc: String,
                            val images: List<String>,val likeCounts: Int,val publishedAt: String,val stars: Int,val title: String,
                            val type: String,val url: String,val views: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        "",
        "",
        "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
        0,
        "",
        0,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        0
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(desc)
        parcel.writeStringList(images)
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleListBean> {
        override fun createFromParcel(parcel: Parcel): ArticleListBean {
            return ArticleListBean(parcel)
        }

        override fun newArray(size: Int): Array<ArticleListBean?> {
            return arrayOfNulls(size)
        }
    }

}

data class ArticleDetailBean(
    @SerializedName("_id") val Id: String,
     val author: String,
     val category: String,
     val content: String,
    @SerializedName("createdAt")
     val createdat: String,
     val desc: String,
     val images: List<String>,
     val index: Int,
    @SerializedName("isOriginal")
     val isoriginal : Boolean,
     val license: String,
    @SerializedName("likeCount")
     val likecount : Int,
    @SerializedName("likeCounts")
     val likecounts : Int,
     val likes: List<String>,
     val markdown: String,
    @SerializedName("originalAuthor")
     val originalauthor: String,
    @SerializedName("publishedAt")
     val publishedat: String,
     val stars : Int,
     val status : Int,
     val tags: List<String>,
     val title: String,
     val type: String,
    @SerializedName("updatedAt")
     val updatedat: String,
     val url: String,
     val views : Int
)

data class Comments(val _id: String,val userId: String,val postId: String,val userName: String,val comment: String,val headUrl: String,
                    val createdAt : String,val secondParentId : String,val secondParentName : String,val ups : String)

