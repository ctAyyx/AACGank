package com.ct.aacgank.common.widget

import android.content.Context
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatImageView

/**
 * 文件名 AutoImageView
 * 创建者  CT
 * 时 间  2019/9/25 11:19
 * TODO  默认宽高比3:4
 */

class AutoImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatImageView(context, attrs, defStyleAttr) {


    constructor(context: Context, attr: AttributeSet?) : this(context,attr,0)
    constructor(context: Context):this(context,null)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height =   MeasureSpec.makeMeasureSpec(width/3*4,MeasureSpec.EXACTLY)

        super.onMeasure(widthMeasureSpec, height)
    }
}