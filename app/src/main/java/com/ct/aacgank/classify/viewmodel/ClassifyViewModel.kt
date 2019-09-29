package com.ct.aacgank.classify.viewmodel

import androidx.lifecycle.ViewModel
import com.ct.aacgank.classify.repository.ClassifyRepository

/**
 * 文件名 ClassifyViewModel
 * 创建者  CT
 * 时 间  2019/9/17 16:58
 * TODO  GanKIo分类数据ViewModel
 */

class ClassifyViewModel(classify: String, repository: ClassifyRepository) : ViewModel() {


    private val repoResult = repository.getClassifyList(classify)

    val girlList = repoResult.pagedList

    val refreshState = repoResult.refreshState

    val networkState = repoResult.networkState

    fun refresh() = repoResult.refresh.invoke()


}