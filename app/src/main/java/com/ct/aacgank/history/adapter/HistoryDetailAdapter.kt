package com.ct.aacgank.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.databinding.ItemNewestBinding

/**
 * 文件名 HistoryDetailAdapter
 * 创建者  CT
 * 时 间  2019/9/26 11:12
 * TODO  历史详情适配器 和最新数据适配器写法差不多 这里没有复用 是为了添加不同的头布局
 */

class HistoryDetailAdapter :
    ListAdapter<ClassifyBean, HistoryDetailAdapter.ViewHolder>(DiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemNewestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


    class ViewHolder(private val binding: ItemNewestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ClassifyBean) {

            with(binding) {
                this.model = model
                isVisible = model.type == "福利"
            }
        }
    }


    class DiffCallBack : DiffUtil.ItemCallback<ClassifyBean>() {
        override fun areItemsTheSame(oldItem: ClassifyBean, newItem: ClassifyBean) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: ClassifyBean, newItem: ClassifyBean) =
            oldItem.id == newItem.id

    }
}
