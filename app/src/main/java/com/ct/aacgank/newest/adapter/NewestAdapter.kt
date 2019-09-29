package com.ct.aacgank.newest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.adapter.BaseRVAdapter
import com.ct.aacgank.databinding.ItemNewestBinding

/**
 * 文件名 NewestAdapter
 * 创建者  CT
 * 时 间  2019/9/20 13:43
 * TODO 最新数据
 */
class NewestAdapter : BaseRVAdapter<ClassifyBean, NewestAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemNewestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        getItem(position)?.let {
            holder.bind(it)
        }
    }


    inner class ViewHolder(private val binding: ItemNewestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ClassifyBean) {

            with(binding) {
                this.model = model
                isVisible = model.type == "福利"
                executePendingBindings()
            }


        }

    }
}