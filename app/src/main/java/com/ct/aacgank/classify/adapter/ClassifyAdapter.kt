package com.ct.aacgank.classify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ct.aacgank.common.adapter.BaseRVAdapter
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.databinding.ItemClassifyBinding

/**
 * 文件名 ClassifyAdapter
 * 创建者  CT
 * 时 间  2019/9/18 9:58
 * TODO  GanKIo分类数据RecycleView适配器 先完成布局 在写代码
 */
class ClassifyAdapter :
    BaseRVAdapter<ClassifyBean, ClassifyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemClassifyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }



     inner class ViewHolder(private val binding: ItemClassifyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ClassifyBean) {
            with(binding) {
                binding.model = model

                executePendingBindings()
            }
        }
    }


}