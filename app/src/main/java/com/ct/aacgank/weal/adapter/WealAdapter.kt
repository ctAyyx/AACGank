package com.ct.aacgank.weal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.common.adapter.BaseRVAdapter
import com.ct.aacgank.databinding.ItemWealBinding

/**
 * 文件名 WealAdapter
 * 创建者  CT
 * 时 间  2019/9/25 10:16
 * TODO
 */

class WealAdapter : BaseRVAdapter<ClassifyBean, WealAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemWealBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, createOnClickListener(it, holder, position))
        }

    }

    override fun createOnClickListener(
        model: ClassifyBean,
        handler: ViewHolder,
        position: Int
    ) = View.OnClickListener {

    }


    class ViewHolder(private val binding: ItemWealBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ClassifyBean, listener: View.OnClickListener) {
            binding.model = model
            binding.clickListener  =listener
            binding.executePendingBindings()
        }
    }
}