package com.ct.aacgank.classify.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ct.aacgank.common.net.NetworkState
import com.ct.aacgank.common.net.ServiceApi
import com.ct.aacgank.classify.data.ClassifyBean
import java.lang.Exception

/**
 * 文件名 PageKeyedClassifyDataSource
 * 创建者  CT
 * 时 间  2019/9/18 14:04
 * TODO  GanKIo分类数据数据源
 */

class PageKeyedClassifyDataSource(
    private val classifyName: String,
    private val serviceApi: ServiceApi
) : PageKeyedDataSource<Int, ClassifyBean>() {


    private var retry: (() -> Any)? = null

    //表示网络请求状态
    val networkState = MutableLiveData<NetworkState>()
    //表示初始化和刷新状态
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFaild() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    init {
        addInvalidatedCallback {
            Log.e("TAG","数据无效化。。。。 ")
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ClassifyBean>
    ) {

        Log.e("TAG","开始请求数据。。。。 ")
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        try {

            val result = serviceApi.getWealList(classifyName,params.requestedLoadSize, 1)
                .execute()
                .body()

            //请求数据成功 不需要重试
            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)

            callback.onResult(result?.data ?: emptyList(), 0, 2)


        } catch (e: Exception) {
            retry = {
                loadInitial(params, callback)
            }

            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ClassifyBean>) {

        networkState.postValue(NetworkState.LOADING)

        try {
            val result = serviceApi.getWealList(classifyName, params.requestedLoadSize, params.key)
                .execute()
                .body()
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(result?.data ?: emptyList(), params.key + 1)

        } catch (e: Exception) {

            retry = {
                loadAfter(params, callback)
            }
            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ClassifyBean>) {
    }

}