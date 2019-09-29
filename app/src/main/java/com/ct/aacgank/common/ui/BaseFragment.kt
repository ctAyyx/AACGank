package com.ct.aacgank.common.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

/**
 * 文件名 BaseFragment
 * 创建者  CT
 * 时 间  2019/9/17 14:48
 * TODO  Fragment基类 便于扩展
 */
open class BaseFragment : Fragment() {

    var lable: String = this.javaClass.simpleName

    var firstShowUser = false//第一次用户可见


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate --> $lable -->${lifecycle.currentState}")

    }

    override fun onStart() {
        super.onStart()

        log("onStart --> $lable -->${lifecycle.currentState}")
    }

    override fun onResume() {
        super.onResume()
        if (!firstShowUser)
            initData()
       log("onResume --> $lable -->${lifecycle.currentState}  $firstShowUser")

    }

    override fun onPause() {
        super.onPause()
        if (!firstShowUser)
            firstShowUser = true
       log("onPause --> $lable -->${lifecycle.currentState}  ")
    }

    override fun onStop() {
        super.onStop()
       log("onStop --> $lable -->${lifecycle.currentState}")
    }

    override fun onDestroy() {
        super.onDestroy()
      log("onDestroy --> $lable -->${lifecycle.currentState}")
    }

    override fun onDetach() {
        super.onDetach()
        log("onDetach --> $lable -->${lifecycle.currentState}")
    }


    open fun initData() {}

     fun log(msg:String,isDBug:Boolean = false){
        if(isDBug)
        Log.e("TAG",msg)
    }



}
