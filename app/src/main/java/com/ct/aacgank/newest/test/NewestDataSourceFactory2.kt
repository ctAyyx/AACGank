package com.ct.aacgank.newest.test


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ct.aacgank.AppDatabase
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.net.ServiceApi

/**
 * 文件名 NewestDataSourceFactory2
 * 创建者  CT
 * 时 间  2019/10/15 13:47
 * TODO
 */
class NewestDataSourceFactory2(private val serviceApi: ServiceApi, private val db: AppDatabase) :
    DataSource.Factory<Int, ClassifyBean>() {
    val sourceLiveData = MutableLiveData<NewestDataSource>()
    override fun create(): DataSource<Int, ClassifyBean> {
        val result = NewestDataSource(serviceApi, db)
        sourceLiveData.postValue(result)
        return result
    }
}