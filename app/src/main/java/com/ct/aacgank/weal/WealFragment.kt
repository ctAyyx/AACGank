package com.ct.aacgank.weal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ct.aacgank.classify.GridItemDecoration
import com.ct.aacgank.classify.repository.ClassifyRepository
import com.ct.aacgank.common.ui.BaseFragment
import com.ct.aacgank.databinding.RefreshRecyclerBinding
import com.ct.aacgank.weal.adapter.WealAdapter
import com.ct.aacgank.weal.viewmodel.WealViewModel
import com.ct.aacgank.weal.viewmodel.WealViewModelFactory

/**
 * 文件名 WealFragment
 * 创建者  CT
 * 时 间  2019/9/25 10:14
 * TODO 福利界面
 */
class WealFragment : BaseFragment() {

    private lateinit var adapter: WealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RefreshRecyclerBinding.inflate(inflater, container, false).apply {

        with(recyclerView) {
            adapter = WealAdapter().also { this@WealFragment.adapter = it }
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(GridItemDecoration(interval = 20))
        }

    }.root


    override fun onResume() {
        super.onResume()

        subscribeUi()
    }

    private fun subscribeUi() {

        val factory = WealViewModelFactory(ClassifyRepository())

        val viewModel = ViewModelProviders.of(this, factory).get(WealViewModel::class.java)

        viewModel.wealList.observe(this, Observer {
            if (!it.isNullOrEmpty())
                adapter.submitList(it)
        })

    }

}
