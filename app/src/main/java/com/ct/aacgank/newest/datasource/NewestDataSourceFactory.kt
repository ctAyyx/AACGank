package com.ct.aacgank.newest.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.net.ServiceApi

/**
 * 文件名 NewestDataSourceFactory
 * 创建者  CT
 * 时 间  2019/9/20 14:50
 * TODO
 */
class NewestDataSourceFactory(private val serviceApi: ServiceApi) :
    DataSource.Factory<Int, ClassifyBean>() {

    val sourceLiveData = MutableLiveData<NewestDataSource>()

    override fun create(): DataSource<Int, ClassifyBean> {

        val source = NewestDataSource(serviceApi)
        sourceLiveData.postValue(source)
        return source
    }

}