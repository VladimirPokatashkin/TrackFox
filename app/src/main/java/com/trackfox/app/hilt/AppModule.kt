package com.trackfox.app.hilt

import android.content.Context
import com.trackfox.app.data.room.Database
import com.trackfox.app.data.room.dao.TrainingDao
import com.trackfox.app.data.room.dao.UserDao
import com.trackfox.app.model.service.UserSessionManager
import com.trackfox.app.service.AuthService
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
    fun provideUserDao(database: Database) : UserDao =
        database.userDao()

    @Provides
    fun provideUserSessionManager(@ApplicationContext context : Context) : UserSessionManager =
        UserSessionManager(context)

    @Provides
    fun provideAuthService(userDao: UserDao, userSessionManager: UserSessionManager) : AuthService
            = AuthService(userDao, userSessionManager)
}