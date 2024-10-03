package com.example.news.data.api


import com.example.news.BuildConfig
import com.example.news.data.api.model.articlesModel.ArticlesResponse
import com.example.news.data.api.model.sourcesModel.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("/v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("category") category: String
    ): SourcesResponse

    @GET("/v2/everything")
    suspend fun getArticles(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("sources") source: String? = null,
        @Query("page") page:Int?=null,
        @Query("pageSize") pageSize:Int?=null
    ): ArticlesResponse

    @GET("/v2/everything")
    suspend fun getArticlesThatHas(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("q") searchQuery: String,
    ): ArticlesResponse

    @GET("/v2/everything")
    suspend fun getArticle(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("q") title: String?=null,
        @Query("searchIn") searchIn:String = "title"
    ): ArticlesResponse
}
