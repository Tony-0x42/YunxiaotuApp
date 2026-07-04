package com.example.cj.videoeditor.di

import android.content.Context
import com.example.cj.videoeditor.network.ApiService
import com.example.cj.videoeditor.network.RetrofitClient
import com.example.cj.videoeditor.network.interceptor.TokenInterceptor
import com.example.cj.videoeditor.network.util.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 网络层 Hilt 依赖注入模块
 *
 * 复用现有 `network` 包下的 [RetrofitClient] 构建配置，
 * 向全应用提供 [ApiService]、[TokenInterceptor]、[TokenManager] 等单例。
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenInterceptor(@ApplicationContext context: Context): TokenInterceptor {
        return TokenInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return RetrofitClient.createOkHttpClient(context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return RetrofitClient.createRetrofit(context)
    }

    @Provides
    @Singleton
    fun provideApiService(@ApplicationContext context: Context): ApiService {
        return RetrofitClient.getApiService(context)
    }

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }
}
