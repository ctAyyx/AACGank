package com.ct.aacgank.common.net

import android.util.SparseArray
import androidx.core.util.set
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 文件名 ServiceNet
 * 创建者  CT
 * 时 间  2019/9/17 15:08
 * TODO
 */
private const val TIME_OUT = 15_000L




class ServiceNet private constructor() {


    private fun initRetrofit(baseUrl: String, client: OkHttpClient?) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client ?: initClient())
            .build()


    private fun initClient() = OkHttpClient.Builder()
        .callTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .addInterceptor(CInterceptor())
        .build()


    companion object {

        private val serviceMap = SparseArray<Retrofit>()

        fun getInstance(baseUrl: String, client: OkHttpClient? = null): Retrofit {
            val retrofit = serviceMap[baseUrl.hashCode()]
            retrofit?.let {
                return it
            }
            serviceMap[baseUrl.hashCode()] = ServiceNet().initRetrofit(baseUrl, client)
            return serviceMap[baseUrl.hashCode()]

        }


    }

}