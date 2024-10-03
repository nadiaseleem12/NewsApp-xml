package com.example.news.data.repository

import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.sourcesModel.Source
import com.example.news.data.connectivity.NetworkHandler
import com.example.news.data.repository.data_sources.local_datat_source.LocalDataSource
import com.example.news.data.repository.data_sources.remote_data_source.RemoteDataSource
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(val networkHandler: NetworkHandler,val localDataSource: LocalDataSource,val remoteDataSource: RemoteDataSource):NewsRepo {
    override suspend fun getSources(categoryId: String): List<Source> {
       if (networkHandler.isNetworkAvailable()){
           val sources = remoteDataSource.getSources(categoryId)
           localDataSource.saveSources(sources)
           return sources
       }else{
           return localDataSource.getSources(categoryId)
       }
    }

    override suspend fun getArticles(sourceId: String,page:Int?,pageSize:Int?): List<Article> {
        if (networkHandler.isNetworkAvailable()){
            val articles = remoteDataSource.getArticles(sourceId,page, pageSize)
            articles.forEach { it.sourceId=sourceId }
            localDataSource.saveArticles(articles)
            return articles
        }else{
           return localDataSource.getArticles(sourceId)
        }
    }

    override suspend fun getArticlesThatHas(searchQuery: String): List<Article> {
        if (networkHandler.isNetworkAvailable()){
            val articles = remoteDataSource.getArticlesThatHas(searchQuery=searchQuery)
            localDataSource.saveArticles(articles)
            return articles
        }else{
            return localDataSource.getArticlesThatHas(searchQuery = searchQuery)
        }
    }

    override suspend fun getArticle(title: String): Article {
        return if (networkHandler.isNetworkAvailable()){
            remoteDataSource.getArticle(title)
        }else{
            localDataSource.getArticle(title)
        }
    }
}
