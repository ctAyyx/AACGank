package com.ct.aacgank

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.dao.ClassifyDao


/**
 * 文件名 AppDatabase
 * 创建者  CT
 * 时 间  2019/9/29 14:22
 * TODO
 */
@Database(entities = [ClassifyBean::class], version = 1)
abstract class AppDatabase : RoomDatabase() {


    //获取分类Dao
    abstract fun classifyDao(): ClassifyDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context): AppDatabase {

            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "gank-db"
            ).build()
        }

    }


}
