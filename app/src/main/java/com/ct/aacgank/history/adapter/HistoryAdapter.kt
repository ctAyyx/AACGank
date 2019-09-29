package com.ct.aacgank.history.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ct.aacgank.common.adapter.BaseRVAdapter
import com.ct.aacgank.databinding.ItemHistoryBinding
import com.ct.aacgank.history.HistoryFragmentDirections
import com.ct.aacgank.history.data.History

/**
 * 文件名 HistoryAdapter
 * 创建者  CT
 * 时 间  2019/9/25 13:45
 * TODO
 */
class HistoryAdapter : BaseRVAdapter<History, HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)
            ?.let {
                holder.bind(it, createOnClickListener(it, holder, position))
            }
    }

    override fun createOnClickListener(
        model: History,
        handler: ViewHolder,
        position: Int
    ) = View.OnClickListener {

        val action =
            HistoryFragmentDirections.actionHistoryFragmentToHistoryDetailFragment(model.dayData.toTypedArray(),model.time)

        it.findNavController().navigate(action)

    }

    class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: History, listener: View.OnClickListener) {
            binding.model = model
            binding.clickListener = listener
            binding.executePendingBindings()
        }

    }
}