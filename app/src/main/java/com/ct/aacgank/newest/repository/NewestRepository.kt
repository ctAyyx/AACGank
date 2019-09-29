package com.ct.aacgank.newest.repository

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.net.BaseRepository
import com.ct.aacgank.common.net.Listing
import com.ct.aacgank.newest.datasource.NewestDataSourceFactory

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

}