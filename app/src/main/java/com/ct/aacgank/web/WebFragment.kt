package com.ct.aacgank.web

import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.ct.aacgank.common.ui.BaseFragment
import com.ct.aacgank.common.util.e
import com.ct.aacgank.databinding.FragmentWebBinding
import kotlin.system.measureTimeMillis

/**
 * 文件名 WebFragment
 * 创建者  CT
 * 时 间  2019/9/27 13:43
 * TODO 展示网页的Fragment
 */

class WebFragment : BaseFragment() {


    private val args by navArgs<WebFragmentArgs>()
    private lateinit var binding: FragmentWebBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWebBinding.inflate(inflater, container, false).apply {
        binding = this

    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createWebView()
    }


    private fun createWebView() {

        val time = measureTimeMillis {
            val webView = WebView(requireContext())
            binding.linearWeb.addView(webView)
            val params = webView.layoutParams as LinearLayout.LayoutParams
            params.width = LinearLayout.LayoutParams.MATCH_PARENT
            params.height = LinearLayout.LayoutParams.MATCH_PARENT
            webView.layoutParams = params

            bindHtmlUrl(webView, args.htmlUrl)

            onBackPressed(webView)

        }


    }


    /**
     * 当回退按钮被点击
     * 如果webView可以回退 则回退webView
     * 否则 关闭界面
     * */
    private fun onBackPressed(webView: WebView) {

        //使用onBackPressedDispatcher来拦截回退按钮的点击事件
        val dispatcher = requireActivity().onBackPressedDispatcher

        dispatcher.addCallback(this@WebFragment, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack())
                    webView.goBack()
                else {
                    //移除监听器 否则会发生OOM
                    remove()
                    dispatcher.onBackPressed()
                }
            }

        })

    }


    private fun bindHtmlUrl(webView: WebView, htmlUrl: String?) {

        htmlUrl.e("网址:")


        with(webView.settings) {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportZoom(true)
            builtInZoomControls = true
            useWideViewPort = true
            loadWithOverviewMode = true
            setAppCacheEnabled(true)
            domStorageEnabled = true

        }



        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true //super.shouldOverrideUrlLoading(view, request)
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed()
            }


        }




        webView.loadUrl(htmlUrl)


    }
}