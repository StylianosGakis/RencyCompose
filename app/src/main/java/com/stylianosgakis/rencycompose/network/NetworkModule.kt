package com.stylianosgakis.rencycompose.network

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val REVOLUT_HOST = "https://hiring.revolut.codes/"
    private const val REVOLUT_RETROFIT_NAME =
        "com.stylianosgakis.rencycompose.network.REVOLUT_RETROFIT_NAME"

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    @Singleton
    @Provides
    @Named(REVOLUT_RETROFIT_NAME)
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(REVOLUT_HOST)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideCurrencyApi(
        @Named(REVOLUT_RETROFIT_NAME) retrofit: Retrofit,
    ): RatesApi =
        retrofit.create(RatesApi::class.java)
}