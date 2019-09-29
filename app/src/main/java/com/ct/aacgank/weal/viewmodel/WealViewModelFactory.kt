package com.ct.aacgank.weal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ct.aacgank.classify.repository.ClassifyRepository

/**
 * 文件名 WealViewModelFactory
 * 创建者  CT
 * 时 间  2019/9/25 10:38
 * TODO
 */
class WealViewModelFactory(private val repository: ClassifyRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WealViewModel(repository) as T
    }
}