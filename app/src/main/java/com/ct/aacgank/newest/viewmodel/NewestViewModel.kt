package com.ct.aacgank.newest.viewmodel

import androidx.lifecycle.ViewModel
import com.ct.aacgank.newest.repository.NewestRepository

/**
 * 文件名 NewestViewModel
 * 创建者  CT
 * 时 间  2019/9/20 14:56
 * TODO
 */
class NewestViewModel(repository: NewestRepository) : ViewModel() {

    private val repoResult = repository.getNewestData()

    val newestData = repoResult.pagedList

    val refreshState = repoResult.refreshState

    val networkState = repoResult.networkState


    fun refresh() = repoResult.refresh.invoke()

    val retry = repoResult.retry


}