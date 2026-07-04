package com.example.cj.videoeditor.network;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 同步调用辅助类。
 *
 * <p>所有 {@code executeSync} 方法都会阻塞当前线程，因此必须在后台线程（如 AsyncTask、Thread、Executor）中调用，
 * 禁止在主线程直接调用。</p>
 */
public class ApiHelper {

    private ApiHelper() {
    }

    /**
     * 同步执行返回 {@link BaseResponse} 的请求。
     *
     * @param call Retrofit Call 对象
     * @return 响应体
     * @throws IOException 网络错误或 HTTP 非 2xx
     */
    public static <T> BaseResponse<T> executeSync(Call<BaseResponse<T>> call) throws IOException {
        Response<BaseResponse<T>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("HTTP " + response.code());
        }
    }

    /**
     * 同步执行返回 {@link PageResponse} 的请求。
     *
     * @param call Retrofit Call 对象
     * @return 响应体
     * @throws IOException 网络错误或 HTTP 非 2xx
     */
    public static <T> PageResponse<T> executePageSync(Call<PageResponse<T>> call) throws IOException {
        Response<PageResponse<T>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("HTTP " + response.code());
        }
    }

    /**
     * 同步执行任意 Retrofit 请求。
     *
     * @param call Retrofit Call 对象
     * @return 响应体
     * @throws IOException 网络错误或 HTTP 非 2xx
     */
    public static <T> T executeRawSync(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("HTTP " + response.code());
        }
    }
}
