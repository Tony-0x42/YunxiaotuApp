package com.example.cj.videoeditor.activity;

import dagger.hilt.android.AndroidEntryPoint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.network.ApiService;
import com.example.cj.videoeditor.network.PageApiCallback;
import com.example.cj.videoeditor.network.dto.BatchDocumentDto;
import com.example.cj.videoeditor.utils.ToastUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

@AndroidEntryPoint
public class PrivacyActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_privacy;
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.privacy_policy));
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
        loadPrivacyPolicy();
    }

    private void loadPrivacyPolicy() {
        progressBar.setVisibility(View.VISIBLE);
        Map<String, String> params = new HashMap<>();
        params.put("documentType", "2");
        params.put("status", "0");
        params.put("pageNum", "1");
        params.put("pageSize", "1");
        apiService.getDocumentList(params).enqueue(new PageApiCallback<BatchDocumentDto>() {
            @Override
            public void onSuccess(long total, List<BatchDocumentDto> rows) {
                progressBar.setVisibility(View.GONE);
                if (rows != null && !rows.isEmpty()) {
                    BatchDocumentDto doc = rows.get(0);
                    if (doc.getDocumentTitle() != null && !doc.getDocumentTitle().isEmpty()) {
                        setTitle(doc.getDocumentTitle());
                    }
                    webView.loadDataWithBaseURL(null, wrapHtml(doc.getContent()), "text/html", "utf-8", null);
                } else {
                    ToastUtil.show(PrivacyActivity.this, "隐私政策内容为空");
                }
            }

            @Override
            public void onError(String msg) {
                progressBar.setVisibility(View.GONE);
                ToastUtil.show(PrivacyActivity.this, "隐私政策加载失败：" + msg);
            }
        });
    }

    private String wrapHtml(String body) {
        if (body == null) body = "";
        return "<html><head><meta name='viewport' content='width=device-width, initial-scale=1.0'><style>body{padding:16px;line-height:1.6;color:#333;}h2{color:#2196F3;}</style></head><body>" + body + "</body></html>";
    }
}
