package com.example.news.data.repository.data_sources.remote_data_source

import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.sourcesModel.Source

interface RemoteDataSource {
    suspend fun getSources(categoryId: String):List<Source>
    suspend fun getArticles(sourceId: String,page:Int?,pageSize:Int?):List<Article>
    suspend fun getArticlesThatHas(searchQuery: String):List<Article>
    suspend fun getArticle(title: String): Article
}