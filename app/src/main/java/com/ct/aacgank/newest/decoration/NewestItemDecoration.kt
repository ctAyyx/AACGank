package com.ct.aacgank.newest.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ct.aacgank.common.util.dp2px

/**
 * 文件名 NewestItemDecoration
 * 创建者  CT
 * 时 间  2019/9/26 13:24
 * TODO  最新数据
 * 分隔线有 1dp 的粗细，
 * 在亮色主题中为黑色并带有 12% 的不透明度，
 * 在暗色主题中为白色并带有 12% 的不透明度
 */
class NewestItemDecoration(private val interval: Int = 1) : RecyclerView.ItemDecoration() {


    private val mPaint = Paint().apply {
        color = Color.parseColor("#EAEAEA")
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)


        val position = parent.getChildAdapterPosition(view)
        outRect.top = 0
        outRect.bottom = interval
        if (position == 0)
            outRect.top = 8.dp2px(parent.context)
        else if (position == parent.adapter?.itemCount ?: 0 - 1)
            outRect.bottom = 8.dp2px(parent.context)
    }


    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val childCount = parent.childCount
        val outRect = Rect()

        for (i in 0 until childCount) {
            outRect.setEmpty()
            val childView = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(childView)
            parent.getDecoratedBoundsWithMargins(childView, outRect)

            val top = outRect.bottom - interval
            val left = 72.dp2px(parent.context)
            val right = parent.width - parent.paddingRight
            val bottom = outRect.bottom

            canvas.drawRect(

                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                mPaint
            )

        }

    }
}