package com.ct.aacgank.common.net

/**
 * 文件名 NetworkState
 * 创建者  CT
 * 时 间  2019/9/18 13:39
 * TODO 网络状态
 */

enum class Status {
    RUNNING,//正在请求
    SUCCESS,//请求成功
    FAILED  //请求失败
}

private const val SUCCESS = 200
private const val ERROR = 0


data class NetworkState private constructor(
    val status: Status,
    val code: Int,
    val msg: String? = null

) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS, SUCCESS)
        val LOADING = NetworkState(Status.RUNNING, SUCCESS)


        fun error(msg: String?,code: Int = ERROR) = NetworkState(Status.FAILED, code, msg)
    }


}