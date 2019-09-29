package com.ct.aacgank.common.util

import android.content.Context
import android.util.TypedValue

/**
 * 文件名 Dimens
 * 创建者  CT
 * 时 间  2019/9/26 13:27
 * TODO
 */
fun Number.dp2px(context: Context) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()


fun Number.sp2px(context: Context) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()

fun Number.px2dp(context: Context):Int{
    val scale = context.resources.displayMetrics.density

    return (this.toInt()/scale + 0.5).toInt()
}