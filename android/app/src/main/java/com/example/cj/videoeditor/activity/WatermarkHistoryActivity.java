package com.example.cj.videoeditor.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.adapter.WatermarkHistoryAdapter;
import com.example.cj.videoeditor.network.ApiService;
import com.example.cj.videoeditor.network.PageApiCallback;
import com.example.cj.videoeditor.network.dto.WatermarkParseDto;
import com.example.cj.videoeditor.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WatermarkHistoryActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    private RecyclerView rvHistory;
    private TextView tvEmpty;
    private ProgressBar progressBar;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_watermark_history;
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.watermark_history_title));
        rvHistory = findViewById(R.id.rv_history);
        tvEmpty = findViewById(R.id.tv_empty);
        progressBar = findViewById(R.id.progress_bar);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        loadHistory();
    }

    private void loadHistory() {
        showLoading(true);
        apiService.getWatermarkParseList().enqueue(new PageApiCallback<WatermarkParseDto>() {
            @Override
            public void onSuccess(long total, List<WatermarkParseDto> rows) {
                showLoading(false);
                List<WatermarkParseDto> list = rows != null ? rows : new ArrayList<>();
                rvHistory.setAdapter(new WatermarkHistoryAdapter(list, item -> {
                    if (item != null) {
                        showPreview(item);
                    }
                }));
                tvEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
                rvHistory.setVisibility(list.isEmpty() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(WatermarkHistoryActivity.this,
                        getString(R.string.watermark_history_load_failed, msg));
                rvHistory.setAdapter(new WatermarkHistoryAdapter(new ArrayList<>(), null));
                tvEmpty.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.GONE);
            }
        });
    }

    private void showPreview(WatermarkParseDto item) {
        new WatermarkPreviewDialog(this, item).show();
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
