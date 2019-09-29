package com.ct.aacgank.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ct.aacgank.history.repository.HistoryRepository

/**
 * 文件名 HistoryViewModelFactory
 * 创建者  CT
 * 时 间  2019/9/25 15:54
 * TODO
 */
class HistoryViewModelFactory(private val repository: HistoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewModel(repository) as T
    }
}