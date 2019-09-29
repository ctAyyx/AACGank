package com.ct.aacgank.common.util

/**
 * 文件名 CLogExt
 * 创建者  CT
 * 时 间  2019/9/29 10:51
 * TODO  使用Kotlin对CLog的补充
 */

fun Any?.v() {
    CLog.v(this)
}

fun Any?.d() {
    CLog.d(this)
}

fun Any?.i() {
    CLog.i(this)

}

fun Any?.w() {
    CLog.w(this)
}

fun Any?.e(desc: String="") {
    CLog.e(desc + "$this")
}

fun Any?.a() {
    CLog.a(this)
}
