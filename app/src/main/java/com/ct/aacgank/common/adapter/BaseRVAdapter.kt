package com.ct.aacgank.common.adapter

import android.view.View
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * 文件名 BaseRVAdapter
 * 创建者  CT
 * 时 间  2019/9/18 10:22
 * TODO
 */
abstract class BaseRVAdapter<T, VH : RecyclerView.ViewHolder> :
    PagedListAdapter<T, VH>(BaseDiffCallback<T>()) {


    open fun createOnClickListener(model: T,handler: VH, position: Int):View.OnClickListener = View.OnClickListener {  }

    class BaseDiffCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem === newItem

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }
}


