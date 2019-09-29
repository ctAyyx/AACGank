package com.ct.aacgank.history.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ct.aacgank.common.net.ServiceApi
import com.ct.aacgank.history.data.History


/**
 * 文件名 HistoryDatasourceFactory
 * 创建者  CT
 * 时 间  2019/9/25 15:37
 * TODO
 */
class HistoryDatasourceFactory(private val serviceApi: ServiceApi) :
    DataSource.Factory<Int, History>() {

    val dataSource = MutableLiveData<HistoryDataSource>()

    override fun create(): DataSource<Int, History> {

        val source = HistoryDataSource(serviceApi)
        dataSource.postValue(source)
        return source
    }

}