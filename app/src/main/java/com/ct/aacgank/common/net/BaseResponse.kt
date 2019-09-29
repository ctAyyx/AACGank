package com.ct.aacgank.common.net

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.google.gson.annotations.SerializedName

/**
 * 文件名 BaseResponse
 * 创建者  CT
 * 时 间  2019/9/17 16:25
 * TODO
 */

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)

//Gankio
data class GankResponse<T>(
    @SerializedName("results")
    val data: T,
    @SerializedName("error")
    val error: Boolean = false,
    @SerializedName("category")
    val category: List<String> = emptyList()
)
