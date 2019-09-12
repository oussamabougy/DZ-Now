package com.example.bougy.dz_now


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Article::class),version = 1)
abstract class ArticleDB: RoomDatabase() {
    abstract fun articleDAO(): ArticleDAO

    companion object {
        private var instance: ArticleDB? = null

        fun getInstance(context: Context):ArticleDB?{
            if (instance == null){
                instance = Room.
                    databaseBuilder(context.applicationContext, ArticleDB::class.java,"article.db")
                    .build()

            }
            return instance

        }
        fun destroyInstance(){
            instance = null
        }
    }
}