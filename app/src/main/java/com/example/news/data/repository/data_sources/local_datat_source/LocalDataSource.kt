package com.example.news.data.repository.data_sources.local_datat_source

import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.sourcesModel.Source

interface LocalDataSource {
    suspend fun getSources(categoryId: String):List<Source>
    suspend fun getArticles(sourceId: String):List<Article>
    suspend fun getArticlesThatHas(searchQuery: String):List<Article>
    suspend fun getArticle(title: String): Article
    suspend fun saveSources(sources:List<Source>)
    suspend fun saveArticles(articles:List<Article>)
}