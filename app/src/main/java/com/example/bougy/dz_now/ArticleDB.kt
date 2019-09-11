package com.example.bougy.dz_now

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


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