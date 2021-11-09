package com.yefimoyevhen.appspot.di

import com.yefimoyevhen.appspot.api.AppspotApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BEARER = "Bearer"
    private const val BASE_URL = "http://opn-interview-service.nn.r.appspot.com/"
    private const val HEADER_AUTHORIZATION = "Authorization"

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, "$BEARER ${generateJWT()}")
                    .build()
                    .let(chain::proceed)
            }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): AppspotApi {
        return retrofit
            .build()
            .create(AppspotApi::class.java)
    }
}