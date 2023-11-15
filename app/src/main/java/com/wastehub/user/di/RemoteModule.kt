package com.wastehub.user.di

import com.wastehub.user.data.remote.services.ApiServices
import com.wastehub.user.data.repository.UserRepositoryImpl
import com.wastehub.user.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiServices): UserRepository {
        return UserRepositoryImpl(apiService)
    }

}