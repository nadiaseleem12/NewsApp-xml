package com.example.news.data.database

import android.content.Context
import androidx.room.Room
import androidx.transition.Visibility.Mode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context):NewsDatabase{
        return Room.databaseBuilder(context,NewsDatabase::class.java,"News_Database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase):NewsDao{
       return newsDatabase.getNewsDao()
    }
}