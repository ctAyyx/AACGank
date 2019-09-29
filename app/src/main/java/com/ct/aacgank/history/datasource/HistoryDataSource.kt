package com.ct.aacgank.history.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.net.NetworkState
import com.ct.aacgank.common.net.ServiceApi
import com.ct.aacgank.history.data.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import kotlin.system.measureTimeMillis

/**
 * 文件名 HistoryDataSource
 * 创建者  CT
 * 时 间  2019/9/25 15:06
 * TODO
 */
class HistoryDataSource(private val serviceApi: ServiceApi) :
    PageKeyedDataSource<Int, History>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    private var historyDates: List<String>? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, History>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        try {
            //第一步获取历史干货日期
            historyDates = serviceApi.getHistoryDay()
                .execute().body()?.data ?: emptyList()

            //获取指定日期数据

            val result = preDays(0, params.requestedLoadSize)

            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(result, 0, 1)

        } catch (e: Exception) {
            networkState.postValue(NetworkState.error(e.message ?: "数据请求失败"))
            initialLoad.postValue(NetworkState.error(e.message ?: "数据请求失败"))
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, History>) {

        networkState.postValue(NetworkState.LOADING)
        try {

            val result = preDays(params.key, params.requestedLoadSize)
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(result, params.key + 1)
        } catch (e: Exception) {
            networkState.postValue(NetworkState.error(e.message ?: "数据请求失败"))
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, History>) {

    }


    /**
     * 准备请求具体时间数据的 时间数据
     * @param key  请求页码
     * @param size 每页数据
     * */
    private fun preDays(key: Int, size: Int): List<History> {
        //数据集合
        val resultList = mutableListOf<History>()
        //由于并发原因 数据可能不是按请求顺序返回 这里使用map存储返回的数据 在排序存入resultList
        val resultMap = mutableMapOf<String, History>()

        //获取请求数据的时间集合
        val requestDates = historyDates?.subList(key * size, key * size + size)
        //在这里使用协程 使用runBlocking阻塞当前线程
        runBlocking(Dispatchers.IO) {
            requestDates?.forEach {
                launch {
                    getDataByDay(it, resultMap)
                }

            }
        }
        //更据请求的日期排列数据
        requestDates?.forEach {
            resultMap[it]?.let { history ->
                resultList.add(history)
            }
        }

        return resultList
    }

    /**
     * 获取指定日期数据
     * @param date 日期
     * @param resultMap 存放数据的集合
     * */
    private fun getDataByDay(date: String, resultMap: MutableMap<String, History>) {
        Log.e("TAG", "当前线程:${Thread.currentThread()}")
        //存放指定日期数据的集合
        val dayData = mutableListOf<ClassifyBean>()

        serviceApi.getGankByDate(date.replace("-", "/")).execute().body()?.data?.forEach {
            if (it.key == "福利") {
                resultMap[date] = History(
                    time = date,
                    imageUrl = it.value[0].url,
                    dayData = dayData
                )

                dayData.addAll(0, it.value)
            } else
                dayData.addAll(it.value)
        }
    }


}