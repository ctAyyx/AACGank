package com.ct.aacgank.common.adapter

import android.net.http.SslError
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ct.aacgank.R
import com.ct.aacgank.web.WebFragmentExtensions

/**
 * 文件名 BindingAdapters
 * 创建者  CT
 * 时 间  2019/9/18 10:03
 * TODO DataBinding适配器
 */
@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, imgUrl: String?) {


    if (!imgUrl.isNullOrEmpty()) {
        Glide
            .with(imageView)
            .load(imgUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            //这里使用占位符 导致图片加载后被拉伸 禁用transition后可以达到预期要过
            // .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_launcher_background)

            .into(imageView)
    }


}


@BindingAdapter("isVisible")
fun bindIsVisible(view: View, visible: Boolean) {
    if (visible) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}

@BindingAdapter("htmlUrl")
fun bindHtmlUrl(webView: WebView, htmlUrl: String?) {
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


@BindingAdapter(value = ["htmlUrl", "title"], requireAll = false)
fun bindReadyGoWeb(view: View, htmlUrl: String?, title: String?) {

    view.setOnClickListener {
        htmlUrl?.let { htmlUrl ->
            val action = WebFragmentExtensions.actionToWebFragment(htmlUrl, title ?: "")
            it.findNavController().navigate(action)
        }


    }


}

