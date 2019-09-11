package com.example.bougy.dz_now

import io.reactivex.Observable
import retrofit2.http.GET

interface RestService {
    @GET("articles")
    fun getArticles(): Observable<List<Article>>
}