package com.ct.aacgank.newest.datasource

import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.datasource.BasePositionDataSource
import com.ct.aacgank.common.net.ServiceApi

/**
 * 文件名 NewestDataSource
 * 创建者  CT
 * 时 间  2019/9/20 14:45
 * TODO  最新数据源
 */

class NewestDataSource(private val serviceApi: ServiceApi) :
    BasePositionDataSource<ClassifyBean>() {


    override fun loadInit(params: LoadInitialParams, callback: LoadInitialCallback<ClassifyBean>) {
        val result = serviceApi.getNewestData()
            .execute()
            .body()
        val data = mutableListOf<ClassifyBean>()
        //对数据简单处理
        result?.data?.entries?.forEach {
            if (it.key == "福利" && it.value.isNotEmpty()) {
                data.add(0, it.value[0])
            } else
                data.addAll(it.value)
        }

        callback.onResult(data, 0, data.size)
    }



//    override fun loadInitial(
//        params: LoadInitialParams,
//        callback: LoadInitialCallback<ClassifyBean>
//    ) {
//        networkState.postValue(NetworkState.LOADING)
//        initialLoad.postValue(NetworkState.LOADING)
//
//        try {
//            val result = serviceApi.getNewestData()
//                .execute()
//                .body()
//            val data = mutableListOf<ClassifyBean>()
//            //对数据简单处理
//            result?.data?.entries?.forEach {
//                if (it.key == "福利" && it.value.isNotEmpty()) {
//                    data.add(0, it.value[0])
//                } else
//                    data.addAll(it.value)
//            }
//            networkState.postValue(NetworkState.LOADED)
//            initialLoad.postValue(NetworkState.LOADED)
//
//            callback.onResult(data, 0, data.size)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            networkState.postValue(NetworkState.error(e.message))
//            initialLoad.postValue(NetworkState.error(e.message))
//
//        }
//
//
//    }

}