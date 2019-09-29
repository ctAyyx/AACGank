package com.ct.aacgank.classify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ct.aacgank.classify.repository.ClassifyRepository

/**
 * 文件名 ClassifyViewModelFactory
 * 创建者  CT
 * 时 间  2019/9/18 16:11
 * TODO  GanKIo分类数据ViewModelFactory
 */

class ClassifyViewModelFactory(private val classify:String,private val repository: ClassifyRepository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClassifyViewModel(classify,repository) as T
    }

}