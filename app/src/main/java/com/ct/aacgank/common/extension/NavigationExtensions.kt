package com.ct.aacgank.common.extension

import android.content.Intent
import android.util.Log
import android.util.SparseArray
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * 文件名 NavigationExtensions
 * 创建者  CT
 * 时 间  2019/9/16 10:47
 * TODO  针对BottomNavigationView的扩展
 */


/**
 *
 * @param navGraphIds 导航视图Id的集合
 * @param fragmentManager
 * @param containerId fragment的id
 * @param intent 用于深度链接
 * */
fun BottomNavigationView.setupWithNavController(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent?
): LiveData<NavController> {

    //创建Tag的数组
    val graphIdToTagMap = SparseArray<String>()

    //创建NavController的可观察对象
    val selectedNavController = MutableLiveData<NavController>()


    var firstFragmentGraphId = 0

    //为每个NavGraph创建一个NavHostFragment
    navGraphIds.forEachIndexed { index, navGraphId ->
        //获取每个NavHostFragment的Tag
        val fragmentTag = getFragmentTag(index)

        //查询 或创建一个NavHostFragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        //获取当前视图的id
        val graphId = navHostFragment.navController.graph.id

        if (index == 0)
            firstFragmentGraphId = graphId

        //保存到数组
        graphIdToTagMap[graphId] = fragmentTag

        //如果BottomNavigationView选择的itemId和视图id相等
        if (this.selectedItemId == graphId) {
            //更新LiveData
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(
                fragmentManager,
                navHostFragment,
                index == 0
            )
        } else
            detachNavHostFragment(fragmentManager, navHostFragment)

    }

    var selectedItemTag = graphIdToTagMap[this.selectedItemId]
    val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectedItemTag == firstFragmentTag

    //当一个导航被选择
    setOnNavigationItemSelectedListener { item ->
        if (!fragmentManager.isStateSaved) {
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]

            if (selectedItemTag != newlySelectedItemTag) {
                fragmentManager.popBackStack(
                    firstFragmentTag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                val selectedFragment =
                    fragmentManager.findFragmentByTag(newlySelectedItemTag) as NavHostFragment

                if (firstFragmentTag != newlySelectedItemTag) {
                    fragmentManager.beginTransaction()
                        .attach(selectedFragment)
                        .setPrimaryNavigationFragment(selectedFragment)
                        .apply {
                            graphIdToTagMap.forEach { _, fragmentTag ->
                                if (fragmentTag != newlySelectedItemTag)
                                    detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                            }
                        }
                        .addToBackStack(firstFragmentTag)
                        .setReorderingAllowed(true)
                        .commit()
                }

                selectedItemTag = newlySelectedItemTag
                isOnFirstFragment = selectedItemTag == firstFragmentTag
                selectedNavController.value = selectedFragment.navController
                return@setOnNavigationItemSelectedListener true
            }

        }

        false
    }



    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag))
            this.selectedItemId = firstFragmentGraphId

        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null)
                controller.navigate(controller.graph.id)
        }
    }


    return selectedNavController
}




private fun getFragmentTag(index: Int) = "bottomNavigation#$index"


/**
 * 返回一个NavHostFragment
 * @param fragmentManager
 * @param fragmentTag
 * @param navGraphId
 * @param containerId
 * */
private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    //获取当前存在的Fragment
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?

    //如果不为null 则返回
    existingFragment?.let {
        return it
    }

    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction()
        .add(containerId, navHostFragment, fragmentTag)
        .commitNow()

    return navHostFragment


}

/**
 *
 *
 * */
private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {

    fragmentManager.beginTransaction()
        .attach(navHostFragment)
        .apply {
            if (isPrimaryNavFragment)
                setPrimaryNavigationFragment(navHostFragment)
        }
        .commitNow()

}

/**
 *
 * */

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {

    fragmentManager.beginTransaction()
        .detach(navHostFragment)
        .commitNow()
}

/**
 * 当NavigationBottom的Item被再次选中
 *
 * */
private fun BottomNavigationView.setupItemReselected(
    graphIdToTagMap: SparseArray<String>,
    fragmentManager: FragmentManager
) {
    setOnNavigationItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                as NavHostFragment

        val navController = selectedFragment.navController
        //回退到起始Destination
        navController.popBackStack(
            navController.graph.startDestination, false
        )
    }
}

/**
 * 判断当前的FragmentManager是否处于可回退状态
 * */
private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {

    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount)
        if (getBackStackEntryAt(index).name == backStackName)
            return true

    return false
}