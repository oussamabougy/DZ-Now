package com.example.bougy.dz_now

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestService {
    @GET("get_news")
    fun getArticles(): Observable<List<Article>>

    @GET("category")
    fun getCategories(): Observable<List<Categorie>>

    @GET("papers")
    fun getNewsPapers(): Observable<List<NewsPaper>>

    @GET("get_videos")
    fun getVideos(): Observable<List<Video>>

    @POST("auth_social/")
    fun googleLogin(
        @Body accessToken : JsonObject
    ): Observable<AuthResponse>
}