package com.week4.getir.apicallexample.di

import com.week4.getir.apicallexample.domain.repositories.UserRepository
import com.week4.getir.apicallexample.data.repositories.UserRepositoryImpl
import com.week4.getir.apicallexample.data.remote.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(apiService: ApiClient): UserRepository =
        UserRepositoryImpl(apiService)

}