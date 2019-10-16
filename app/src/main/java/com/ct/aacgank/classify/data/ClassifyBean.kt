package com.ct.aacgank.classify.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


import com.google.gson.annotations.SerializedName

/**
 * 文件名 ClassifyBean
 * 创建者  CT
 * 时 间  2019/9/17 16:16
 * TODO  GanKIo分类数据实体
 */

@Entity(tableName = "classifies")
data class ClassifyBean(

    @PrimaryKey
    @ColumnInfo(name = "_id")
    @SerializedName("_id")
    val id: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("createdAt")
    val createdAt: String? = "",

    @SerializedName("desc")
    val desc: String? = "",

    @SerializedName("publishedAt")
    val publishedAt: String? = "",

    @SerializedName("source")
    val source: String? = "",

    @SerializedName("used")
    val used: Boolean = false,

    @SerializedName("who")
    val who: String? = "",

    @Ignore
    @SerializedName("images")
    val images: List<String>? = null

) : Parcelable {
    /**
     * _id : 57b64b6d421aa93a804bea26
     * createdAt : 2016-08-19T07:57:33.576Z
     * desc : 8-19
     * publishedAt : 2016-08-19T11:26:30.163Z
     * source : chrome
     * type : 福利
     * url : http://ww4.sinaimg.cn/large/610dc034jw1f6yq5xrdofj20u00u0aby.jpg
     * used : true
     * who : daimajia
     */

    constructor(
        id: String,

        url: String,

        type: String,

        createdAt: String? = "",

        desc: String? = "",

        publishedAt: String? = "",

        source: String? = "",

        used: Boolean = false,

        who: String? = ""

    ) : this(id, url, type, createdAt, desc, publishedAt, source, used, who, null)


    constructor(parcel: Parcel) : this(

        id = parcel.readString() ?: "",
        url = parcel.readString() ?: "",
        type = parcel.readString() ?: "",
        createdAt = parcel.readString(),
        desc = parcel.readString(),
        publishedAt = parcel.readString(),
        source = parcel.readString(),
        used = false,
        who = parcel.readString(),
        images = mutableListOf<String>().also { parcel.readStringList(it) }

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        with(parcel) {

            writeString(id)
            writeString(url)
            writeString(type)
            writeString(createdAt)
            writeString(desc)
            writeString(publishedAt)
            writeString(source)
            writeString(who)
            writeStringList(images)
        }


    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ClassifyBean> {
        override fun createFromParcel(parcel: Parcel): ClassifyBean {
            return ClassifyBean(parcel)
        }

        override fun newArray(size: Int): Array<ClassifyBean?> {
            return arrayOfNulls(size)
        }
    }

}