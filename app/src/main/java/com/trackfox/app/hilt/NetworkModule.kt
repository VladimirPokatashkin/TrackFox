package com.trackfox.app.hilt

import com.trackfox.app.BuildConfig
import com.trackfox.app.network.AuthorizedClient
import com.trackfox.app.network.PublicClient
import com.trackfox.app.server.service.AuthRequestService
import com.trackfox.app.server.service.RefreshRequestService
import com.trackfox.app.server.service.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @PublicClient
    fun provideAuthApiService(@PublicClient retrofit: Retrofit) : AuthRequestService =
        retrofit.create(AuthRequestService::class.java)

    @Provides
    @Singleton
    fun provideJson() : Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    @PublicClient
    fun providePublicRetrofit(
        @PublicClient okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .addConverterFactory(json.asConverterFactory("application/problem+json".toMediaType()))
        .build()

    @Provides
    @Singleton
    @PublicClient
    fun providePublicOkHttpClient() : OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    @Singleton
    @PublicClient
    fun provideRefreshRequestService(@PublicClient retrofit : Retrofit): RefreshRequestService =
        retrofit.create(RefreshRequestService::class.java)

    @Provides
    @Singleton
    @AuthorizedClient
    fun provideAuthorizedRetrofit(
        @AuthorizedClient okHttpClient: OkHttpClient,
        json: Json
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()


    @Provides
    @Singleton
    @AuthorizedClient
    fun provideAuthorizedOkHttpClient(tokenAuthenticator: TokenAuthenticator) : OkHttpClient =
        OkHttpClient.Builder()
            .authenticator(tokenAuthenticator)
            .build()
}