package com.trackfox.app.hilt

import com.trackfox.app.server.service.RequestService
import com.trackfox.app.server.service.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : RequestService =
        retrofit.create(RequestService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenAuthenticator: TokenAuthenticator) : OkHttpClient =
        OkHttpClient.Builder()
            .authenticator(tokenAuthenticator)
            .build()
}