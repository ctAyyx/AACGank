package com.ct.aacgank.newest.decoration

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 文件名 NewestRoofItemDecoration
 * 创建者  CT
 * 时 间  2019/9/26 15:13
 * TODO
 */

class NewestRoofItemDecoration(private val onHeadTop: OnHeadListener) :
    RecyclerView.ItemDecoration() {

    interface OnHeadListener {
        fun onHead2Top(position: Int)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        onHeadTop.onHead2Top(parent.getChildAdapterPosition(view))

    }
}