package com.yefimoyevhen.appspot.di

import android.content.Context
import androidx.room.Room
import com.yefimoyevhen.appspot.database.AppspotDatabase
import com.yefimoyevhen.appspot.database.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideAppspotDb(@ApplicationContext context: Context): AppspotDatabase {
        return Room
            .databaseBuilder(
                context,
                AppspotDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideExchangeDAO(appspotDatabase: AppspotDatabase) =
        appspotDatabase.appspotDao()
}