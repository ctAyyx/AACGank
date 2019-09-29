package com.ct.aacgank.web

import android.os.Bundle
import androidx.navigation.NavDirections
import com.ct.aacgank.R

/**
 * 文件名 WebFragmentExtensions
 * 创建者  CT
 * 时 间  2019/9/29 9:54
 * TODO
 */
class WebFragmentExtensions private constructor() {

    data class ActionToWebFragment(val htmlUrl: String, val title: String) : NavDirections {
        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putString("htmlUrl", this.htmlUrl)
            result.putString("title", this.title)
            return result
        }

        override fun getActionId(): Int {
            return R.id.action_global_webFragment
        }


    }

    companion object{
        fun  actionToWebFragment(htmlUrl: String,title: String) =
            ActionToWebFragment(htmlUrl, title)
    }

}