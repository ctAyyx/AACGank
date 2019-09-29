package com.ct.aacgank.common.net

import com.ct.aacgank.classify.data.ClassifyBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 文件名 ServiceApi
 * 创建者  CT
 * 时 间  2019/9/17 16:00
 * TODO
 */
interface ServiceApi {

    /**
     * 获取最新数据
     * */
    @GET("today ")
    fun getNewestData(): Call<GankResponse<Map<String, List<ClassifyBean>>>>


    /**
     * 获取分类数据
     * @param classify 分类
     * @param pageSize 每页请求数据个数
     * @param page 请求第几页
     * */
    @GET("data/{classify}/{pageSize}/{page}")
    fun getWealList(@Path("classify") classify: String, @Path("pageSize") pageSize: Int, @Path("page") page: Int): Call<GankResponse<List<ClassifyBean>>>


    /**
     * 获取发过干货日期接口:
     * */
    @GET("day/history ")
    fun getHistoryDay(): Call<GankResponse<List<String>>>

    /**
     * 每日数据
     * @param date 日期
     * */
    @GET("day/{date}")
    fun getGankByDate(@Path("date") date: String): Call<GankResponse<Map<String, List<ClassifyBean>>>>

}