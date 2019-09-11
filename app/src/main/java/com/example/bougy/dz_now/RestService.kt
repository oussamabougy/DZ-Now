package com.example.bougy.dz_now

import io.reactivex.Observable
import retrofit2.http.GET

interface RestService {
    @GET("get_news")
    fun getArticles(): Observable<List<Article>>

    @GET("categories")
    fun getCategories(): Observable<List<Categorie>>

    @GET("papers")
    fun getNewsPapers(): Observable<List<NewsPaper>>
}