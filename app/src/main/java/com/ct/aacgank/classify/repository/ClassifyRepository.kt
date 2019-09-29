package com.ct.aacgank.classify.repository

import androidx.lifecycle.Transformations
import com.ct.aacgank.Constants
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.classify.datasource.ClassifyDataSourceFactory
import com.ct.aacgank.common.net.BaseRepository
import com.ct.aacgank.common.net.Listing
import com.ct.aacgank.common.extension.toPagedLiveData

/**
 * 文件名 ClassifyRepository
 * 创建者  CT
 * 时 间  2019/9/18 13:35
 * TODO  GanKIo分类数据仓库
 */
class ClassifyRepository : BaseRepository() {


    fun getClassifyList(classify: String): Listing<ClassifyBean> {

        //获取数据源工厂
        val sourceFactory =
            ClassifyDataSourceFactory(classifyName = classify, serviceApi = getServiceApi())


        //prefetchDistance设置paging预加载数量 ps Paging会更据界面显示数据的多少来加载数据
        val livePagedList = sourceFactory.toPagedLiveData(pageSize = Constants.PAGING_LOAD_SIZE)
//        val livePagedList = LivePagedListBuilder(sourceFactory, Config(pageSize = 10,initialLoadSizeHint = 1))
//            .setFetchExecutor(ArchTaskExecutor.getIOThreadExecutor())
//            .build()

        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.initialLoad
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFaild()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            }
        )

    }

}
