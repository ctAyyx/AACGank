package com.ct.aacgank.common.extension

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.paging.*
import java.util.concurrent.Executor

/**
 * 文件名 LivePagedListExtension
 * 创建者  CT
 * 时 间  2019/9/25 17:03
 * TODO  对Paging配置 修改默认initialLoadSizeHint
 */

fun <Key, Value> DataSource.Factory<Key, Value>.toPagedLiveData(
    pageSize: Int,
    initialLoadKey: Key? = null,
    boundaryCallback: PagedList.BoundaryCallback<Value>? = null,
    fetchExecutor: Executor = ArchTaskExecutor.getIOThreadExecutor()
): LiveData<PagedList<Value>> {
    return LivePagedListBuilder(this, Config(pageSize = pageSize, initialLoadSizeHint = pageSize))
        .setInitialLoadKey(initialLoadKey)
        .setBoundaryCallback(boundaryCallback)
        .setFetchExecutor(fetchExecutor)
        .build()
}
