package com.example.cj.videoeditor.network

import android.content.Context
import com.example.cj.videoeditor.network.interceptor.TokenInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit 客户端单例。
 *
 * 通过 [getApiService] 可获取 [ApiService] 实例；
 * 也可配合 Hilt 在 [com.example.cj.videoeditor.di.NetworkModule] 中注入。
 */
object RetrofitClient {

    /**
     * 创建 Gson 实例，允许空值与宽松解析，减少后端字段缺失导致的崩溃。
     */
    private fun createGson(): Gson {
        return GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()
    }

    /**
     * 创建 OkHttpClient，包含 Token 拦截器与日志拦截器。
     */
    fun createOkHttpClient(context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(NetworkConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(TokenInterceptor(context))

        if (NetworkConfig.ENABLE_HTTP_LOG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(logging)
        }

        return builder.build()
    }

    /**
     * 创建 Retrofit 实例。
     */
    fun createRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .build()
    }

    /**
     * 获取 [ApiService] 实例。
     */
    fun getApiService(context: Context): ApiService {
        return createRetrofit(context).create(ApiService::class.java)
    }
}
