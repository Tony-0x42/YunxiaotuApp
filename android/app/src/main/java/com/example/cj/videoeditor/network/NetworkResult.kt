package com.example.cj.videoeditor.network

/**
 * 网络请求结果封装，便于 Repository/ViewModel 统一处理成功/失败/加载状态。
 */
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val code: Int, val message: String?) : NetworkResult<Nothing>()
    data class Exception(val throwable: Throwable) : NetworkResult<Nothing>()
}

/**
 * 将 [BaseResponse] 转换为 [NetworkResult]
 */
fun <T> BaseResponse<T>.toNetworkResult(): NetworkResult<T> {
    return if (isSuccess()) {
        val body = data
        if (body == null) {
            NetworkResult.Error(code, msg ?: "返回数据为空")
        } else {
            NetworkResult.Success(body)
        }
    } else {
        NetworkResult.Error(code, msg ?: "请求失败")
    }
}

/**
 * 将 [PageResponse] 转换为 [NetworkResult]
 */
fun <T> PageResponse<T>.toNetworkResult(): NetworkResult<List<T>> {
    return if (isSuccess()) {
        NetworkResult.Success(getSafeRows())
    } else {
        NetworkResult.Error(code, msg ?: "请求失败")
    }
}
