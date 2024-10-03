package com.example.news.data.repository.data_sources.local_datat_source

import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.sourcesModel.Source
import com.example.news.data.database.NewsDao
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val newsDao: NewsDao):LocalDataSource {
    override suspend fun getSources(categoryId: String): List<Source> {
        return newsDao.getSources(categoryId)
    }

    override suspend fun getArticles(sourceId: String): List<Article> {
        return newsDao.getArticles(sourceId)
    }

    override suspend fun getArticlesThatHas(searchQuery: String): List<Article> {
       return newsDao.searchArticles("%$searchQuery%")
    }

    override suspend fun getArticle(title: String): Article {
        return newsDao.getArticle(title)
    }

    override suspend fun saveSources(sources: List<Source>) {
     newsDao.saveSource(sources)
    }

    override suspend fun saveArticles(articles: List<Article>) {
        newsDao.saveArticles(articles)
    }
}
