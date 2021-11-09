package com.yefimoyevhen.appspot.di

import com.yefimoyevhen.appspot.api.AppspotApi
import com.yefimoyevhen.appspot.database.AppspotDao
import com.yefimoyevhen.appspot.repository.AppspotRepository
import com.yefimoyevhen.appspot.repository.AppspotRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesAppspotRepository(api: AppspotApi, dao: AppspotDao):
            AppspotRepository = AppspotRepositoryImpl(api, dao)
}