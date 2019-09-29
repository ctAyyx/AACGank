package com.ct.aacgank.history.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ct.aacgank.common.util.dp2px

/**
 * 文件名 HistoryItemDecoration
 * 创建者  CT
 * 时 间  2019/9/26 15:30
 * TODO
 */
class HistoryItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        outRect.bottom = 8.dp2px(parent.context)
        outRect.top = 0
        if (position == 0)
            outRect.top = 8.dp2px(parent.context)


    }
}