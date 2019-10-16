package com.ct.aacgank.newest.repository

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagedList

/**
 * 文件名 NetWorkBoundResource
 * 创建者  CT
 * 时 间  2019/10/14 16:43
 * TODO
 */
abstract class NetWorkBoundResource<ResultType>
@MainThread constructor() {
    private val result = MediatorLiveData<ResultType>()

    init {
        //从数据库获取数据
        val dbSource = loadFromDb()
        Log.e("TAG", "数据库数据:${dbSource.value}")
        result.addSource(dbSource) {
            //当数据发生改变
            result.removeSource(dbSource)
            if (shouldFetch(it)) {
                //从网络获取数据
                fetchFromNetwork(dbSource)

            } else {
                result.addSource(dbSource) { newData ->
                    setValue(newData)
                }
            }
        }

    }


    private fun setValue(newValue: ResultType) {
        if (result.value != newValue)
            result.value = newValue
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            setValue(newData)
        }

        result.addSource(apiResponse) {

            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            saveCallResult(it)
            result.addSource(loadFromDb()) { newData ->
                setValue(newData)
            }

        }

    }

    fun asLiveData() = result as LiveData<ResultType>
    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun saveCallResult(item: ResultType)

    protected abstract fun createCall(): LiveData<ResultType>
}