package com.ct.aacgank.history.viewmodel

import androidx.lifecycle.ViewModel
import com.ct.aacgank.history.repository.HistoryRepository

/**
 * 文件名 HistoryViewModel
 * 创建者  CT
 * 时 间  2019/9/25 15:47
 * TODO
 */

class HistoryViewModel(repository: HistoryRepository) : ViewModel() {

    private val repoResult = repository.getHistory()

    val historyList = repoResult.pagedList

    val networkState = repoResult.networkState

    val refreshState = repoResult.refreshState

    fun refresh() = repoResult.refresh.invoke()

}