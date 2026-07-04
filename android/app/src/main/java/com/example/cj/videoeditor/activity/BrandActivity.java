package com.example.cj.videoeditor.activity;

import dagger.hilt.android.AndroidEntryPoint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.adapter.BrandAdapter;
import com.example.cj.videoeditor.bean.Brand;
import com.example.cj.videoeditor.network.ApiCallback;
import com.example.cj.videoeditor.network.ApiService;
import com.example.cj.videoeditor.network.dto.BatchBrandDto;
import com.example.cj.videoeditor.utils.ToastUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

@AndroidEntryPoint
public class BrandActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    private RecyclerView rvBrands;
    private TextView tvEmpty;
    private ProgressBar progressBar;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_brand;
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.brand));
        rvBrands = findViewById(R.id.rv_brands);
        tvEmpty = findViewById(R.id.tv_empty);
        progressBar = findViewById(R.id.progress_bar);
        rvBrands.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void initData() {
        loadBrands();
    }

    private void loadBrands() {
        showLoading(true);
        apiService.getBrandList().enqueue(new ApiCallback<List<BatchBrandDto>>() {
            @Override
            public void onSuccess(List<BatchBrandDto> data) {
                showLoading(false);
                List<Brand> brands = convert(data);
                if (brands.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    rvBrands.setVisibility(View.GONE);
                } else {
                    tvEmpty.setVisibility(View.GONE);
                    rvBrands.setVisibility(View.VISIBLE);
                    rvBrands.setAdapter(new BrandAdapter(brands, brand -> {
                        ToastUtil.show(BrandActivity.this, brand.getName() + "\n" + brand.getDetail());
                    }));
                }
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(BrandActivity.this, "品牌信息加载失败：" + msg);
                tvEmpty.setVisibility(View.VISIBLE);
                rvBrands.setVisibility(View.GONE);
            }
        });
    }

    private List<Brand> convert(List<BatchBrandDto> rows) {
        List<Brand> list = new ArrayList<>();
        if (rows == null) return list;
        for (BatchBrandDto dto : rows) {
            List<String> media = new ArrayList<>();
            if (dto.getMediaUrls() != null && !dto.getMediaUrls().isEmpty()) {
                media = Arrays.asList(dto.getMediaUrls().split(","));
            }
            list.add(new Brand(
                    dto.getBrandId() != null ? String.valueOf(dto.getBrandId()) : "",
                    dto.getBrandName(),
                    dto.getLogoUrl(),
                    dto.getIntro(),
                    dto.getDetail(),
                    media
            ));
        }
        return list;
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
