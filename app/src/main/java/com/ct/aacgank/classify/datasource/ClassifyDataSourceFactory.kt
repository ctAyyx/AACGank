package com.ct.aacgank.classify.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ct.aacgank.common.net.ServiceApi
import com.ct.aacgank.classify.data.ClassifyBean


/**
 * 文件名 ClassifyDataSourceFactory
 * 创建者  CT
 * 时 间  2019/9/18 14:25
 * TODO  GanKIo分类数据源工程
 */

class ClassifyDataSourceFactory(
    private val classifyName: String,
    private val serviceApi: ServiceApi
) : DataSource.Factory<Int, ClassifyBean>() {
    val sourceLiveData = MutableLiveData<PageKeyedClassifyDataSource>()
    override fun create(): DataSource<Int, ClassifyBean> {
        val source =
            PageKeyedClassifyDataSource(classifyName, serviceApi)
        sourceLiveData.postValue(source)
        return source
    }

}