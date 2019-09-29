package com.ct.aacgank.history.repository

import androidx.lifecycle.Transformations
import com.ct.aacgank.common.net.BaseRepository
import com.ct.aacgank.common.net.Listing
import com.ct.aacgank.history.data.History
import com.ct.aacgank.history.datasource.HistoryDatasourceFactory
import com.ct.aacgank.common.extension.toPagedLiveData

/**
 * 文件名 HistoryRepository
 * 创建者  CT
 * 时 间  2019/9/25 15:40
 * TODO
 */
class HistoryRepository : BaseRepository() {

    fun getHistory(): Listing<History> {

        val factory = HistoryDatasourceFactory(getServiceApi())

        val result = factory.toPagedLiveData(5)



        return Listing(
            pagedList = result,
            networkState = Transformations.switchMap(factory.dataSource) {
                it.networkState
            },
            refreshState = Transformations.switchMap(factory.dataSource) {
                it.initialLoad
            },
            refresh = { factory.dataSource.value?.invalidate() },
            retry = {}
        )

    }
}