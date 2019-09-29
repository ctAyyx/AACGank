package com.ct.aacgank.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct.aacgank.classify.LinearItemDecoration
import com.ct.aacgank.common.net.NetworkState
import com.ct.aacgank.common.net.Status
import com.ct.aacgank.common.ui.BaseFragment
import com.ct.aacgank.databinding.RefreshRecyclerBinding
import com.ct.aacgank.history.adapter.HistoryAdapter
import com.ct.aacgank.history.decoration.HistoryItemDecoration
import com.ct.aacgank.history.repository.HistoryRepository
import com.ct.aacgank.history.viewmodel.HistoryViewModel
import com.ct.aacgank.history.viewmodel.HistoryViewModelFactory

/**
 * 文件名 HistoryFragment
 * 创建者  CT
 * 时 间  2019/9/25 13:29
 * TODO
 */
class HistoryFragment : BaseFragment() {

    private lateinit var adapter: HistoryAdapter
    private lateinit var binding: RefreshRecyclerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RefreshRecyclerBinding.inflate(inflater, container, false).apply {

        with(recyclerView) {
            adapter = HistoryAdapter().also { this@HistoryFragment.adapter = it }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(HistoryItemDecoration())


        }

        binding = this
        subscribeUi()

    }.root


    private fun subscribeUi() {


        val viewModel = ViewModelProviders.of(this, HistoryViewModelFactory(HistoryRepository()))
            .get(HistoryViewModel::class.java)

        viewModel.historyList.observe(this, Observer {
            if (!it.isNullOrEmpty())
                adapter.submitList(it)
        })


        viewModel.networkState.observe(this, Observer {
            if (it.status == Status.FAILED)
                Log.e("TAG", "请求失败$it")
        })


        viewModel.refreshState.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = it == NetworkState.LOADING
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}