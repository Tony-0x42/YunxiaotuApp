package com.example.cj.videoeditor.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 全局网络加载状态。
 *
 * UI 层可观察 [loadingFlow] 并在需要时显示/隐藏加载动画。
 * 当前未绑定 UI，后续页面接入真实接口时可直接使用。
 */
@Singleton
class NetworkLoadingState @Inject constructor() {

    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow: StateFlow<Boolean> = _loadingFlow.asStateFlow()

    private var loadingCount = 0

    fun show() {
        loadingCount++
        _loadingFlow.value = true
    }

    fun hide() {
        if (loadingCount > 0) {
            loadingCount--
        }
        if (loadingCount == 0) {
            _loadingFlow.value = false
        }
    }

    fun reset() {
        loadingCount = 0
        _loadingFlow.value = false
    }
}
