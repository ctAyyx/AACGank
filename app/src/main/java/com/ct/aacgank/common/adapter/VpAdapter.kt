package com.ct.aacgank.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ct.aacgank.common.ui.BaseFragment

/**
 * 文件名 VpAdapter
 * 创建者  CT
 * 时 间  2019/9/12 14:35
 * TODO ViewPager的适配器
 */

class VpAdapter(fm: FragmentManager, private val fragments: List<BaseFragment>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int): CharSequence? =
      fragments[position].lable

}