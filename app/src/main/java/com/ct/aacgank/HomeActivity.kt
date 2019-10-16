package com.ct.aacgank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ct.aacgank.common.extension.setupWithNavController
import com.ct.aacgank.databinding.ActivityHomeBinding

/**
 *
 * AACGank项目入口界面
 *
 * 本界面使用DrawerLayout + NavigationView + BottomNavigationView
 * 难点:关于BottomNavigationView动态切换NavHostFragment
 *
 * */

class HomeActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)

        //设置Toolbar
        setSupportActionBar(binding.toolbar)


        val navGraphIds =
            listOf(R.navigation.nav_newest, R.navigation.nav_classify, R.navigation.nav_weal)
        binding.bottomHome.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.fragment_home,
            intent = intent
        ).observe(this, Observer {
            appBarConfiguration = AppBarConfiguration(it.graph, binding.drawer)
            //设置视图与侧滑栏 toolbar的联动
            setupActionBarWithNavController(it, appBarConfiguration)
            binding.navigationHome.setupWithNavController(it)

            it.addOnDestinationChangedListener { controller, destination, args ->
                //我们在这里拦截
                setTitle(destination, args)

                if (destination.id != controller.graph.startDestination)
                    binding.bottomHome.visibility = View.GONE
                else
                    binding.bottomHome.visibility = View.VISIBLE

            }

            navController = it


        })


    }

    /**
     * 设置标题 要设置标题的destination
     * */
    private fun setTitle(destination: NavDestination, arguments: Bundle?) {
        if (!arguments?.getString("title").isNullOrEmpty()) {
            //Log.e("TAG","修改标签 ${ arguments?.getString("title")}")
            destination.label = arguments?.getString("title")
            supportActionBar?.title = arguments?.getString("title")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
