package com.example.news.data.repository

import com.example.news.data.repository.data_sources.local_datat_source.LocalDataSource
import com.example.news.data.repository.data_sources.local_datat_source.LocalDataSourceImpl
import com.example.news.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.news.data.repository.data_sources.remote_data_source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindNewsRepo(newsRepoImpl: NewsRepoImpl):NewsRepo

    @Binds
    fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl):LocalDataSource

    @Binds
    fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl):RemoteDataSource

}