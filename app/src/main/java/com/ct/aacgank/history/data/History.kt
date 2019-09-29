package com.ct.aacgank.history.data

import com.ct.aacgank.classify.data.ClassifyBean

/**
 * 文件名 History
 * 创建者  CT
 * 时 间  2019/9/25 15:25
 * TODO
 */
data class History(val time: String, val imageUrl: String, val dayData: List<ClassifyBean>)