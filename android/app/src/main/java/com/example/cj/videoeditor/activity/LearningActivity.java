package com.example.cj.videoeditor.activity;

import dagger.hilt.android.AndroidEntryPoint;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.adapter.MaterialAdapter;
import com.example.cj.videoeditor.bean.Material;
import com.example.cj.videoeditor.network.ApiCallback;
import com.example.cj.videoeditor.network.ApiService;
import com.example.cj.videoeditor.network.PageApiCallback;
import com.example.cj.videoeditor.network.dto.BatchTutorialCategoryDto;
import com.example.cj.videoeditor.network.dto.BatchTutorialDto;
import com.example.cj.videoeditor.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

@AndroidEntryPoint
public class LearningActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    private TabLayout tabLayout;
    private RecyclerView rvMaterial;
    private TextView tvEmpty;
    private ProgressBar progressBar;
    private List<Material> allMaterials;
    private List<BatchTutorialCategoryDto> categories = new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_learning;
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.learning));
        tabLayout = findViewById(R.id.tab_layout);
        rvMaterial = findViewById(R.id.rv_material);
        tvEmpty = findViewById(R.id.tv_empty);
        progressBar = findViewById(R.id.progress_bar);
        rvMaterial.setLayoutManager(new LinearLayoutManager(this));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadMaterialsByCategory(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    protected void initData() {
        loadCategories();
    }

    private void loadCategories() {
        showLoading(true);
        apiService.getTutorialCategoryAll().enqueue(new ApiCallback<List<BatchTutorialCategoryDto>>() {
            @Override
            public void onSuccess(List<BatchTutorialCategoryDto> data) {
                showLoading(false);
                categories.clear();
                if (data != null) {
                    categories.addAll(data);
                }
                setupTabs();
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(LearningActivity.this, "分类加载失败：" + msg);
                categories.clear();
                setupTabs();
            }
        });
    }

    private void setupTabs() {
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        for (BatchTutorialCategoryDto category : categories) {
            String name = category.getCategoryName() != null ? category.getCategoryName() : "";
            tabLayout.addTab(tabLayout.newTab().setText(name));
        }
        tabLayout.getTabAt(0).select();
    }

    private void loadMaterialsByCategory(int position) {
        showLoading(true);
        Map<String, String> params = new HashMap<>();
        params.put("status", "0");
        if (position > 0 && position <= categories.size()) {
            BatchTutorialCategoryDto category = categories.get(position - 1);
            if (category.getCategoryId() != null) {
                params.put("categoryId", String.valueOf(category.getCategoryId()));
            }
        }
        apiService.getTutorialList(params).enqueue(new PageApiCallback<BatchTutorialDto>() {
            @Override
            public void onSuccess(long total, List<BatchTutorialDto> rows) {
                showLoading(false);
                allMaterials = convert(rows);
                refreshList();
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(LearningActivity.this, "学习资料加载失败：" + msg);
                allMaterials = new ArrayList<>();
                refreshList();
            }
        });
    }

    private List<Material> convert(List<BatchTutorialDto> rows) {
        List<Material> list = new ArrayList<>();
        if (rows == null) return list;
        for (BatchTutorialDto dto : rows) {
            Material item = new Material();
            item.setId(dto.getTutorialId() != null ? String.valueOf(dto.getTutorialId()) : "");
            item.setTitle(dto.getTutorialTitle());
            item.setType("1".equals(dto.getTutorialType()) ? Material.Type.VIDEO : Material.Type.DOCUMENT);
            item.setCoverUrl(dto.getCoverUrl());
            item.setContentUrl("1".equals(dto.getTutorialType()) ? dto.getVideoUrl() : dto.getDocumentContent());
            item.setPublishTime(dto.getUpdateTime());
            item.setViewCount(dto.getViewCount() != null ? dto.getViewCount() : 0);
            list.add(item);
        }
        return list;
    }

    private void refreshList() {
        rvMaterial.setAdapter(new MaterialAdapter(allMaterials));
        tvEmpty.setVisibility(allMaterials.isEmpty() ? View.VISIBLE : View.GONE);
        rvMaterial.setVisibility(allMaterials.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
