package com.ct.aacgank.newest.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.ct.aacgank.AppDatabase
import com.ct.aacgank.Constants
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.extension.toPagedLiveData
import com.ct.aacgank.common.net.BaseRepository
import com.ct.aacgank.common.net.Listing
import com.ct.aacgank.common.net.NetworkState
import com.ct.aacgank.newest.datasource.NewestDataSourceFactory
import com.ct.aacgank.newest.test.NewestBoundaryCallback
import com.ct.aacgank.newest.test.NewestDataSourceFactory2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * 文件名 NewestRepository
 * 创建者  CT
 * 时 间  2019/9/20 14:56
 * TODO
 */

class NewestRepository : BaseRepository() {


    fun getNewestData(): Listing<ClassifyBean> {
        //创建数据源
        val newestFactory = NewestDataSourceFactory(getServiceApi())

        val livePagedList = newestFactory.toLiveData(1)


        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(newestFactory.sourceLiveData) {
                it.networkState
            },
            refreshState = Transformations.switchMap(newestFactory.sourceLiveData) {
                it.initialLoad
            },
            refresh = { newestFactory.sourceLiveData.value?.invalidate() },
            retry = {}
        )


    }


    fun getNewestData2(context: Context): Listing<ClassifyBean> {
        //从数据库获取数据
        //没有数据从后台获取
        //并将从后台获取的数据存入数据库


        //创建数据库边界回调
        val boundaryCallback = NewestBoundaryCallback(getServiceApi(), handleResponse = {
            runBlocking(Dispatchers.IO) {
                AppDatabase.getInstance(context).classifyDao()
                    .insertClassifies(it)
            }

        })


        val livePagedList = AppDatabase.getInstance(context).classifyDao().getAllClassifies2()
            .toLiveData(pageSize = 10, boundaryCallback = boundaryCallback)

        return Listing(
            pagedList = livePagedList,
            refreshState = MutableLiveData<NetworkState>(),
            networkState = MutableLiveData<NetworkState>(),
            refresh = { },
            retry = {}
        )

    }


    fun getClassifyData(context: Context): Listing<ClassifyBean> {
        val newestDataSourceFactory =
            NewestDataSourceFactory2(
                getServiceApi(),
                AppDatabase.getInstance(context)

            )

        val livepagedList = newestDataSourceFactory.toPagedLiveData(pageSize = 20)
        return Listing(
            pagedList = livepagedList,
            networkState = Transformations.switchMap(newestDataSourceFactory.sourceLiveData) {
                it.networkState
            },
            refreshState = Transformations.switchMap(newestDataSourceFactory.sourceLiveData) {
                it.initialLoad
            },
            retry = {},
            refresh = {
                newestDataSourceFactory.sourceLiveData.value?.invalidate()
            }
        )
    }

}