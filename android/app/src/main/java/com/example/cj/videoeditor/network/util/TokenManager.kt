package com.example.cj.videoeditor.network.util

import android.content.Context
import com.example.cj.videoeditor.utils.AppConfig
import com.example.cj.videoeditor.utils.SharedPrefUtil
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Token 管理器：封装 Token 的存取与清除。
 *
 * 登录成功后调用 [saveToken]；退出登录时调用 [clearToken]。
 */
@Singleton
class TokenManager @Inject constructor(
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: Context
) {

    fun saveToken(token: String?) {
        if (token.isNullOrBlank()) return
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_TOKEN, token)
    }

    fun getToken(): String {
        return SharedPrefUtil.getString(context, AppConfig.SP_KEY_TOKEN, "") ?: ""
    }

    fun clearToken() {
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_TOKEN, "")
    }

    fun hasToken(): Boolean = getToken().isNotBlank()
}
