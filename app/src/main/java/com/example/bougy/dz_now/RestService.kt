package com.example.bougy.dz_now

import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface RestService {
    @GET("get_news/")
    fun getArticles(): Observable<List<Article>>

    @GET("category/")
    fun getCategories(): Observable<List<Categorie>>

    @GET("source/")
    fun getSources(): Observable<List<Source>>

    @GET("get_videos/")
    fun getVideos(): Observable<List<Video>>

    @POST("auth_social/")
    fun googleLogin(
        @Body accessToken : JsonObject
    ): Observable<AuthResponse>

    @GET("category_favorite/")
    fun getFavoriteCategories(
        @Header("Authorization") token:String
    ): Observable<List<FavoriteCategory>>

    @GET("source_favorite/")
    fun getFavoriteSites(
        @Header("Authorization") token:String
    ): Observable<List<Source>>

    @GET("actuality_favorite/")
    fun getFavoriteArticles(
        @Header("Authorization") token:String
    ):Observable<List<Article>>

    @DELETE("category_favorite/{id}/")
    fun deleteFavoriteCategory(
        @Header("Authorization") token:String,
        @Path("id") id:Int
    ): Completable


    @DELETE("source_favorite/{id}/")
    fun deleteFavoriteSite(
        @Header("Authorization") token:String,
        @Path("id") id: Int
    ): Completable

    @DELETE("actuality_favorite/{id}/")
    fun deleteFavoriteArticle(
        @Header("Authorization") token:String,
        @Path("id") id:Int
    ): Completable


    @FormUrlEncoded
    @POST("category_favorite/")
    fun addFavoriteCategory(
        @Header("Authorization") token:String,
        @Field("category") id:Int
    ): Observable<Categorie>

    @FormUrlEncoded
    @POST("source_favorite/")
    fun addFavoriteSite(
        @Header("Authorization") token:String,
        @Field("source") id:Int
    ): Observable<Source>

    @FormUrlEncoded
    @POST("actuality_favorite/")
    fun addFavoriteArticle(
        @Header("Authorization") token:String,
        @Field("category") id:Int
    ): Observable<Article>


}