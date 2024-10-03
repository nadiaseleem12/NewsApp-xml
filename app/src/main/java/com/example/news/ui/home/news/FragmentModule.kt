package com.example.news.ui.home.news

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun provideArticlesAdapter(): ArticlesAdapter {
        return ArticlesAdapter()
    }
}