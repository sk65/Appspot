package com.yefimoyevhen.appspot.di

import android.content.Context
import com.yefimoyevhen.appspot.util.InternetChecker
import com.yefimoyevhen.appspot.util.InternetCheckerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    @Singleton
    fun providesInternetChecker(@ApplicationContext context: Context):
            InternetChecker = InternetCheckerImpl(context)
}