package com.ct.aacgank.common.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import com.ct.aacgank.common.net.NetworkState
import java.lang.Exception
import java.security.Key

/**
 * 文件名 BasePageKeyedDataSoource
 * 创建者  CT
 * 时 间  2019/10/15 16:44
 * TODO  分页查询基础数据源
 */

/**
 * 根据Key加载下一页数据
 * */
abstract class BasePageKeyedDataSource<Key, Value> : PageKeyedDataSource<Key, Value>() {
    //表示网络请求状态
    val networkState = MutableLiveData<NetworkState>()
    //表示初始化和刷新状态
    val initialLoad = MutableLiveData<NetworkState>()


    init {
        addInvalidatedCallback {
            onInvalidated()
        }
    }

    abstract fun loadInit(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, Value>
    )

    abstract fun loadMore(params: LoadParams<Key>, callback: LoadCallback<Key, Value>)

    /**
     *在数据源失效时调用
     * */
    open fun onInvalidated() {}


    override fun loadInitial(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, Value>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        try {
            loadInit(params, callback)
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
        } catch (e: Exception) {
            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }

    }


    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        networkState.postValue(NetworkState.LOADING)
        try {
            loadMore(params, callback)
            networkState.postValue(NetworkState.LOADED)
        } catch (e: Exception) {
            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
        }
    }


    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
    }


}

/**
 * 根据最后一个item来加载下一页数据
 * */
abstract class BaseItemKeyedDataSource<Key, Value> : ItemKeyedDataSource<Key, Value>() {

    //表示网络请求状态
    val networkState = MutableLiveData<NetworkState>()
    //表示初始化和刷新状态
    val initialLoad = MutableLiveData<NetworkState>()

    init {
        addInvalidatedCallback { }
    }

    init {
        addInvalidatedCallback {
            onInvalidated()
        }
    }

    abstract fun loadInit(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Value>
    )

    abstract fun loadMore(params: LoadParams<Key>, callback: LoadCallback<Value>)

    /**
     *在数据源失效时调用
     * */
    open fun onInvalidated() {}


    override fun loadInitial(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Value>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        try {
            loadInit(params, callback)
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
        } catch (e: Exception) {
            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Value>) {
        networkState.postValue(NetworkState.LOADING)
        try {
            loadMore(params, callback)
            networkState.postValue(NetworkState.LOADED)
        } catch (e: Exception) {
            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
        }
    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Value>) {

    }

}

abstract class BasePositionDataSource<Value> : PositionalDataSource<Value>() {
    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

     init {
         addInvalidatedCallback {
             onInvalidated()
         }
     }


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Value>) {
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Value>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        try {

            loadInit(params, callback)
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
        } catch (e: Exception) {
            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    abstract fun loadInit(params: LoadInitialParams, callback: LoadInitialCallback<Value>)
    /**
     *在数据源失效时调用
     * */
    open fun onInvalidated() {}
}