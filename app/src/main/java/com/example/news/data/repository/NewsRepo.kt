package com.example.news.data.repository

import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.sourcesModel.Source
import retrofit2.http.Query

interface NewsRepo {
    suspend fun getSources(categoryId: String):List<Source>
    suspend fun getArticles(sourceId: String,page:Int?,pageSize:Int?):List<Article>
    suspend fun getArticlesThatHas(searchQuery: String):List<Article>
    suspend fun getArticle(title: String):Article
}