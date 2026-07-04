package com.example.cj.videoeditor.network

/**
 * 网络层全局常量
 *
 * 后端默认端口 8080，上下文路径为 /。
 * Android Emulator 访问本机后端请使用 10.0.2.2，真机请替换为电脑局域网 IP。
 */
object NetworkConfig {

    /**
     * 后端 Base URL，末尾必须带 /
     */
    const val BASE_URL = "http://192.168.1.5:8080/"

    /**
     * 连接/读取/写入超时（秒）
     */
    const val TIMEOUT_SECONDS = 30L

    /**
     * 是否开启 OkHttp 请求/响应日志
     */
    const val ENABLE_HTTP_LOG = true

    /**
     * 后端 JWT 认证头前缀
     */
    const val TOKEN_PREFIX = "Bearer "
}
