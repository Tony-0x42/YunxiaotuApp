package com.example.cj.videoeditor.utils;

import com.example.cj.videoeditor.R;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.core.content.ContextCompat;

import java.io.File;

/**
 * 系统 DownloadManager 下载工具，用于将网络图片/视频保存到系统相册/媒体目录。
 */
public class DownloadUtil {

    public interface OnDownloadResult {
        void onResult(boolean success, String localUri);
    }

    private static final String SUB_DIR = "BatchVideo";

    /**
     * 下载图片到 Pictures/BatchVideo/。
     *
     * @param context  上下文
     * @param url      图片 URL
     * @param listener 结果回调（可为 null）
     * @return 系统 DownloadManager 分配的下载任务 ID
     */
    public static long downloadImage(Context context, String url, OnDownloadResult listener) {
        String fileName = generateFileName(url, "image_", getImageExtension(url));
        return enqueueDownload(context, url, fileName, Environment.DIRECTORY_PICTURES, listener);
    }

    /**
     * 下载视频到 Movies/BatchVideo/。
     *
     * @param context  上下文
     * @param url      视频 URL
     * @param listener 结果回调（可为 null）
     * @return 系统 DownloadManager 分配的下载任务 ID
     */
    public static long downloadVideo(Context context, String url, OnDownloadResult listener) {
        String fileName = generateFileName(url, "video_", getVideoExtension(url));
        return enqueueDownload(context, url, fileName, Environment.DIRECTORY_MOVIES, listener);
    }

    private static long enqueueDownload(Context context, String url, String fileName,
                                        String dirType, OnDownloadResult listener) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (listener != null) {
                listener.onResult(false, null);
            }
            return -1;
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(fileName);
        request.setDescription(context.getString(R.string.app_name));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(dirType, SUB_DIR + File.separator + fileName);
        request.allowScanningByMediaScanner();

        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (dm == null) {
            if (listener != null) {
                listener.onResult(false, null);
            }
            return -1;
        }

        long downloadId = dm.enqueue(request);
        registerCompletionReceiver(context, downloadId, listener);
        return downloadId;
    }

    private static void registerCompletionReceiver(Context context, long downloadId, OnDownloadResult listener) {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent == null || !DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                    return;
                }
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id != downloadId) {
                    return;
                }

                try {
                    ctx.unregisterReceiver(this);
                } catch (Exception ignored) {
                    // 防止重复反注册崩溃
                }

                DownloadManager dm = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                if (dm == null) {
                    notifyResult(listener, false, null);
                    return;
                }

                Cursor cursor = null;
                try {
                    cursor = dm.query(new DownloadManager.Query().setFilterById(downloadId));
                    if (cursor != null && cursor.moveToFirst()) {
                        int status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            String uri = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                            notifyResult(listener, true, uri);
                        } else {
                            notifyResult(listener, false, null);
                        }
                    } else {
                        notifyResult(listener, false, null);
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ContextCompat.registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);
        } else {
            context.registerReceiver(receiver, filter);
        }
    }

    private static void notifyResult(OnDownloadResult listener, boolean success, String localUri) {
        if (listener != null) {
            listener.onResult(success, localUri);
        }
    }

    private static String generateFileName(String url, String prefix, String extension) {
        String name = String.valueOf(System.currentTimeMillis());
        if (!TextUtils.isEmpty(url)) {
            try {
                String path = Uri.parse(url).getLastPathSegment();
                if (!TextUtils.isEmpty(path)) {
                    int dot = path.lastIndexOf('.');
                    if (dot > 0) {
                        name = path.substring(0, dot) + "_" + System.currentTimeMillis();
                    } else {
                        name = path + "_" + System.currentTimeMillis();
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return prefix + name + "." + extension;
    }

    private static String getImageExtension(String url) {
        String ext = extractExtension(url);
        if (!TextUtils.isEmpty(ext)) {
            String lower = ext.toLowerCase();
            if (lower.equals("jpg") || lower.equals("jpeg") || lower.equals("png")
                    || lower.equals("webp") || lower.equals("gif") || lower.equals("bmp")) {
                return lower;
            }
        }
        return "jpg";
    }

    private static String getVideoExtension(String url) {
        String ext = extractExtension(url);
        if (!TextUtils.isEmpty(ext)) {
            String lower = ext.toLowerCase();
            if (lower.equals("mp4") || lower.equals("mov") || lower.equals("3gp")
                    || lower.equals("mkv") || lower.equals("webm")) {
                return lower;
            }
        }
        return "mp4";
    }

    private static String extractExtension(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        try {
            String path = Uri.parse(url).getPath();
            if (TextUtils.isEmpty(path)) {
                return null;
            }
            String fileName = new File(path).getName();
            int dot = fileName.lastIndexOf('.');
            return dot > 0 && dot < fileName.length() - 1 ? fileName.substring(dot + 1) : null;
        } catch (Exception e) {
            return null;
        }
    }
}
