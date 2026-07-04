package com.example.cj.videoeditor.network.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.cj.videoeditor.network.NetworkResult
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 统一错误处理：解析异常信息并弹出 Toast。
 *
 * 可在 Repository/ViewModel 中调用 [handle] 或 [showToast]。
 */
@Singleton
class NetworkErrorHandler @Inject constructor(
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: Context
) {

    /**
     * 解析 Throwable 为可读文本。
     */
    fun parseMessage(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    401 -> "登录已过期，请重新登录"
                    403 -> "暂无权限访问该资源"
                    404 -> "请求的资源不存在"
                    500 -> "服务器内部错误"
                    else -> throwable.message ?: "网络请求失败"
                }
            }
            is SocketTimeoutException -> "连接超时，请稍后重试"
            is IOException -> "网络连接失败，请检查网络"
            else -> throwable.message ?: "请求异常"
        }
    }

    /**
     * 处理 [NetworkResult.Exception]，弹出提示。
     */
    fun handle(result: NetworkResult.Exception) {
        showToast(parseMessage(result.throwable))
    }

    /**
     * 直接处理 Throwable。
     */
    fun handle(throwable: Throwable) {
        showToast(parseMessage(throwable))
    }

    /**
     * 在主线程弹出 Toast。
     */
    fun showToast(message: String?) {
        if (message.isNullOrBlank()) return
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
