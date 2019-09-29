package com.ct.aacgank.weal.viewmodel

import androidx.lifecycle.ViewModel
import com.ct.aacgank.classify.repository.ClassifyRepository

/**
 * 文件名 WealViewModel
 * 创建者  CT
 * 时 间  2019/9/25 10:35
 * TODO
 */
class WealViewModel(repository: ClassifyRepository) :ViewModel(){

    private val repoResult = repository.getClassifyList("福利")

    val wealList = repoResult.pagedList

    val networkState = repoResult.networkState

    val refreshState = repoResult.refreshState




}