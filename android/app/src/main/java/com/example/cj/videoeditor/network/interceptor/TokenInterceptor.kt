package com.example.cj.videoeditor.network.interceptor

import android.content.Context
import com.example.cj.videoeditor.network.NetworkConfig
import com.example.cj.videoeditor.utils.AppConfig
import com.example.cj.videoeditor.utils.SharedPrefUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Token 拦截器：从 SharedPreferences 读取 JWT Token 并添加到 Authorization 请求头。
 */
class TokenInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = SharedPrefUtil.getString(context, AppConfig.SP_KEY_TOKEN, "")

        val newRequest = if (!token.isNullOrBlank()) {
            request.newBuilder()
                .header("Authorization", NetworkConfig.TOKEN_PREFIX + token)
                .build()
        } else {
            request
        }

        return chain.proceed(newRequest)
    }
}
