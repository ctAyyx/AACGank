package com.ct.aacgank.weal.datasource

import androidx.paging.PageKeyedDataSource
import com.ct.aacgank.classify.data.ClassifyBean

/**
 * 文件名 WealDataSource
 * 创建者  CT
 * 时 间  2019/9/25 10:33
 * TODO
 */
class WealDataSource :PageKeyedDataSource<Int,ClassifyBean>(){
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ClassifyBean>
    ) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ClassifyBean>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ClassifyBean>) {

    }

}