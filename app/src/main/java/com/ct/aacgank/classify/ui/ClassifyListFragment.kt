package com.ct.aacgank.classify.ui

import android.os.Bundle

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct.aacgank.classify.adapter.ClassifyAdapter
import com.ct.aacgank.common.net.*
import com.ct.aacgank.databinding.RefreshRecyclerBinding
import com.ct.aacgank.common.ui.BaseFragment
import com.ct.aacgank.classify.repository.ClassifyRepository
import com.ct.aacgank.classify.viewmodel.ClassifyViewModel
import com.ct.aacgank.classify.viewmodel.ClassifyViewModelFactory
import com.ct.aacgank.newest.decoration.NewestItemDecoration

/**
 * 文件名 ClassifyListFragment
 * 创建者  CT
 * 时 间  2019/9/17 14:54
 * TODO  GanKIo分类数据列表展示界面
 */


class ClassifyListFragment : BaseFragment() {

    private lateinit var adapter: ClassifyAdapter
    private lateinit var binding: RefreshRecyclerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RefreshRecyclerBinding.inflate(inflater, container, false).apply {
        binding = this
        with(recyclerView) {
            adapter = ClassifyAdapter().also { this@ClassifyListFragment.adapter = it }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(NewestItemDecoration())
        }

    }.root

    override fun onResume() {
        super.onResume()
        subscribeUi(adapter, binding)
    }


    private fun subscribeUi(adapter: ClassifyAdapter, binding: RefreshRecyclerBinding) {

        val factory = ClassifyViewModelFactory(lable, ClassifyRepository())

        val girlVM = ViewModelProviders.of(this, factory).get(ClassifyViewModel::class.java)

        girlVM.girlList.observe(this, Observer {
            if (!it.isNullOrEmpty())
                adapter.submitList(it)
        })

        binding.swipeRefresh.setOnRefreshListener {
            girlVM.refresh()
        }

        girlVM.refreshState.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = it == NetworkState.LOADING
        })


        girlVM.networkState.observe(this, Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(requireContext(), "网络异常:${it.msg}", Toast.LENGTH_LONG).show()
        })

    }


}