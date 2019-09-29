package com.ct.aacgank.common.net

import com.ct.aacgank.Constants

/**
 * 文件名 BaseRepository
 * 创建者  CT
 * 时 间  2019/9/20 14:58
 * TODO
 */
open class BaseRepository {

    open fun getServiceApi(): ServiceApi =
        ServiceNet.getInstance(Constants.BASE_URL_GANK).create(ServiceApi::class.java)


}