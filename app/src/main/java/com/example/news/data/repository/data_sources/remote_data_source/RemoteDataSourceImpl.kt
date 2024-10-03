package com.example.news.data.repository.data_sources.remote_data_source

import com.example.news.data.api.WebServices
import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.sourcesModel.Source
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val webServices: WebServices):RemoteDataSource {
    override suspend fun getSources(categoryId: String): List<Source> {
        return webServices.getSources(category = categoryId).sources ?: listOf()
    }

    override suspend fun getArticles(sourceId: String,page:Int?,pageSize:Int?): List<Article> {
        return webServices.getArticles(source = sourceId).articles ?: listOf()
    }

    override suspend fun getArticlesThatHas(searchQuery: String): List<Article> {
        return webServices.getArticlesThatHas(searchQuery = searchQuery).articles ?: listOf()
    }

    override suspend fun getArticle(title: String): Article {
       return webServices.getArticle(title=title).articles?.get(0) ?: Article()
    }
}
