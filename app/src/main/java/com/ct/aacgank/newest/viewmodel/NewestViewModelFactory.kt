package com.ct.aacgank.newest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ct.aacgank.newest.repository.NewestRepository

/**
 * 文件名 NewestViewModelFactory
 * 创建者  CT
 * 时 间  2019/9/20 15:29
 * TODO
 */

class NewestViewModelFactory(private val repository: NewestRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewestViewModel(repository) as T
    }
}