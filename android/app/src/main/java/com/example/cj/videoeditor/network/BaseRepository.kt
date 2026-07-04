package com.example.cj.videoeditor.network

import javax.inject.Inject

/**
 * 基础仓库封装：统一处理加载状态与异常捕获。
 *
 * 后续各 Repository 可继承此类，通过 [safeApiCall] 发起请求。
 */
open class BaseRepository @Inject constructor(
    private val loadingState: NetworkLoadingState
) {

    /**
     * 安全地执行接口请求。
     *
     * @param showLoading 是否触发全局加载状态
     * @param block 实际的 suspend 请求块
     */
    suspend fun <T> safeApiCall(
        showLoading: Boolean = true,
        block: suspend () -> NetworkResult<T>
    ): NetworkResult<T> {
        return try {
            if (showLoading) loadingState.show()
            block()
        } catch (e: Exception) {
            NetworkResult.Exception(e)
        } finally {
            if (showLoading) loadingState.hide()
        }
    }
}
