package com.ct.aacgank.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ct.aacgank.classify.data.ClassifyBean

/**
 * 文件名 ClassifyDao
 * 创建者  CT
 * 时 间  2019/9/29 14:16
 * TODO
 */
@Dao
interface ClassifyDao {

    @Query("SELECT * FROM classifies")
    fun getAllClassifies(): LiveData<List<ClassifyBean>>

    @Insert
    fun insertClassifies(classifies: List<ClassifyBean>)


    @Query("SELECT * FROM classifies")
    fun getAllClassifies2(): DataSource.Factory<Int, ClassifyBean>

    /**
     * 分页查询
     * */
    @Query("SELECT * FROM classifies LIMIT :startPage,:pageSize")
    fun getClassifiesLimit(startPage: Int, pageSize: Int): List<ClassifyBean>

    @Query("SELECT * FROM classifies")
    fun getClassifies():List<ClassifyBean>

    @Delete
    fun deleteClassifies(classifies: List<ClassifyBean>)

//    @Delete
//    fun deleteAll()

}