package com.week4.getir.apicallexample.di

import com.week4.getir.apicallexample.data.remote.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient {
        return ApiClient()
    }
}