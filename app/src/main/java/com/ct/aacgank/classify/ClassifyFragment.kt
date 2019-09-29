package com.ct.aacgank.classify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ct.aacgank.R
import com.ct.aacgank.common.adapter.VpAdapter
import com.ct.aacgank.classify.ui.ClassifyListFragment
import com.ct.aacgank.common.ui.BaseFragment
import com.ct.aacgank.databinding.FragmentClassifyBinding

/**
 * 文件名 ClassifyFragment
 * 创建者  CT
 * 时 间  2019/9/12 14:45
 * TODO   分类模块
 */
class ClassifyFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentClassifyBinding.inflate(inflater, container, false).apply {
        val tabs = resources.getStringArray(R.array.gank_tab)
        val mFragments = mutableListOf<BaseFragment>()
        tabs.forEach {

            mFragments.add(
                ClassifyListFragment().apply { lable = it }
            )
        }
        vpGank.adapter = VpAdapter(childFragmentManager, mFragments)
        vpGank.offscreenPageLimit = mFragments.size

    }.root


}