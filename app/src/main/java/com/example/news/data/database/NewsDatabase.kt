package com.example.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.data.api.model.articlesModel.Article
import com.example.news.data.api.model.sourcesModel.Source

@Database(entities = [Source::class,Article::class], version = 2)
abstract class NewsDatabase:RoomDatabase() {
    abstract fun getNewsDao():NewsDao
}