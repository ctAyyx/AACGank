package com.ct.aacgank.newest.test

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ct.aacgank.AppDatabase
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.net.NetworkState
import com.ct.aacgank.common.net.ServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.lang.Exception

/**
 * 文件名 NewestDataSource
 * 创建者  CT
 * 时 间  2019/10/15 11:16
 * TODO  默认初始化数据从数据库加载
 *       如果没有数据则从后台获取
 *       加载更多不向数据库中添加数据
 *       数据库只保存最新的分页数据
 */
class NewestDataSource(
    private val serviceApi: ServiceApi,
    private val db: AppDatabase

) :
    PageKeyedDataSource<Int, ClassifyBean>() {

    //表示网络请求状态
    val networkState = MutableLiveData<NetworkState>()
    //表示初始化和刷新状态
    val initialLoad = MutableLiveData<NetworkState>()


    init {
        addInvalidatedCallback {
            runBlocking(Dispatchers.IO) {
                db.classifyDao().deleteClassifies(db.classifyDao().getClassifies())
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ClassifyBean>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        try {
            //获取数据库数据
            val dbResource = db.classifyDao()
                .getClassifiesLimit(0, params.requestedLoadSize)
            Log.e("TAG", "初始化数据---> ${dbResource.size}")
            if (dbResource.isNullOrEmpty()) {
                //从后台加在数据
                val netResource = serviceApi.getWealList("Android", params.requestedLoadSize, 1)
                    .execute()
                    .body()

                callback.onResult(netResource?.data ?: emptyList(), 1, 2)
                //向数据库添加数据
                runBlocking {
                    Log.e("TAG", "存入的数据:${netResource?.data?.size}")
                    db.classifyDao().insertClassifies(netResource?.data ?: emptyList())
                }
            } else
                callback.onResult(dbResource, 1, 2)
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
        } catch (e: Exception) {
            e.printStackTrace()

            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ClassifyBean>) {
        networkState.postValue(NetworkState.LOADING)
        try {

            //从后台加载数据
            val response =
                serviceApi.getWealList("Android", params.requestedLoadSize, params.key)
                    .execute()
                    .body()
            callback.onResult(response?.data ?: emptyList(), params.key + 1)
            networkState.postValue(NetworkState.LOADED)

        } catch (e: Exception) {
            e.printStackTrace()
            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ClassifyBean>) {
    }

}