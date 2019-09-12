package com.example.bougy.dz_now

import android.arch.persistence.room.*

@Dao
interface ArticleDAO {
    @Query("SELECT * FROM article")
    fun getArticles():List<Article>

    @Query("SELECT * FROM article WHERE title =:arg0")
    fun getArticle(arg0:String):Article

    @Query("Select count(*) from article")
    fun getCount():Int

    @Insert
    fun add(article: Article)

    @Update
    fun modifier(article: Article)

    @Delete
    fun delete(article: Article)
}