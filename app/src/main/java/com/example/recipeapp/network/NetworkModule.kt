package com.example.recipeapp.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory





@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_KEY = "api_key"
    @Provides @Singleton
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .build()
        val req = chain.request().newBuilder().url(newUrl).build()
        chain.proceed(req)
    }

    @Provides @Singleton
    fun provideOkHttp(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(ok: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .client(ok)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides @Singleton
    fun provideApi(retrofit: Retrofit): SpoonacularApi =
        retrofit.create(SpoonacularApi::class.java)
}
