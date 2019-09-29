package com.ct.aacgank.history.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import com.ct.aacgank.common.ui.BaseFragment
import com.ct.aacgank.databinding.RefreshRecyclerBinding
import com.ct.aacgank.history.adapter.HistoryDetailAdapter

/**
 * 文件名 HistoryDetailFragment
 * 创建者  CT
 * 时 间  2019/9/26 10:22
 * TODO
 */
class HistoryDetailFragment : BaseFragment() {


    private val args: HistoryDetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController().graph.label
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RefreshRecyclerBinding.inflate(inflater, container, false).apply {
        val dateList = args.dateList

        with(recyclerView) {
            adapter = HistoryDetailAdapter().also { it.submitList(dateList.toList()) }
            layoutManager = LinearLayoutManager(requireContext())


        }
        swipeRefresh.isEnabled = false

    }.root
}