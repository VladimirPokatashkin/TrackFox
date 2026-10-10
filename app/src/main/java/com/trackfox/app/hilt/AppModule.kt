package com.trackfox.app.hilt

import android.content.Context
import com.trackfox.app.data.room.Database
import com.trackfox.app.data.room.dao.TrainingDao
import com.trackfox.app.network.PublicClient
import com.trackfox.app.server.service.AuthRequestService
import com.trackfox.app.server.service.AuthorizationService
import com.trackfox.app.server.service.RefreshRequestService
import com.trackfox.app.service.UserSessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context) : Database =
        Database.getDatabase(context)

    @Provides
    fun provideTrainingDao(database: Database) : TrainingDao =
        database.trainingDao()

    @Provides
    fun provideUserSessionManager(
        @ApplicationContext context : Context,
        @PublicClient requestService: RefreshRequestService
    ) : UserSessionManager =
        UserSessionManager(context, requestService)

    @Provides
    @Singleton
    fun provideAuthService(
        userSessionManager: UserSessionManager,
        @PublicClient requestService: AuthRequestService
    ) : AuthorizationService =
        AuthorizationService(userSessionManager, requestService)
}