package com.example.cj.videoeditor.activity;

import dagger.hilt.android.AndroidEntryPoint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.adapter.DocumentAdapter;
import com.example.cj.videoeditor.bean.Document;
import com.example.cj.videoeditor.network.ApiCallback;
import com.example.cj.videoeditor.network.ApiService;
import com.example.cj.videoeditor.network.PageApiCallback;
import com.example.cj.videoeditor.network.dto.BatchDocumentDto;
import com.example.cj.videoeditor.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

@AndroidEntryPoint
public class DocumentActivity extends BaseActivity {

    public static final String EXTRA_DOCUMENT_ID = "document_id";

    @Inject
    ApiService apiService;

    private RecyclerView rvDocuments;
    private TextView tvEmpty;
    private ProgressBar progressBar;
    private TabLayout tabLayout;
    private List<String> categoryList = new ArrayList<>();
    private Long targetDocumentId;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_document;
    }

    @Override
    protected void initViews() {
        setTitle("新手指南");
        tabLayout = findViewById(R.id.tab_layout);
        rvDocuments = findViewById(R.id.rv_documents);
        tvEmpty = findViewById(R.id.tv_empty);
        progressBar = findViewById(R.id.progress_bar);
        rvDocuments.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        targetDocumentId = getIntent().getLongExtra(EXTRA_DOCUMENT_ID, 0L);
        if (targetDocumentId == null || targetDocumentId <= 0) {
            loadCategories();
        } else {
            loadDocumentDetail(targetDocumentId);
        }
    }

    private void loadCategories() {
        showLoading(true);
        apiService.getDocumentCategoryAll().enqueue(new ApiCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                showLoading(false);
                categoryList = data != null ? data : new ArrayList<>();
                setupTabs();
                loadDocuments("");
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(DocumentActivity.this, "分类加载失败：" + msg);
                categoryList = new ArrayList<>();
                setupTabs();
                loadDocuments("");
            }
        });
    }

    private void setupTabs() {
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        for (String category : categoryList) {
            tabLayout.addTab(tabLayout.newTab().setText(category));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    loadDocuments("");
                } else {
                    loadDocuments(categoryList.get(tab.getPosition() - 1));
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadDocuments(String category) {
        showLoading(true);
        Map<String, String> params = new HashMap<>();
        params.put("documentType", "3");
        params.put("status", "0");
        params.put("pageNum", "1");
        params.put("pageSize", "1000");
        if (category != null && !category.isEmpty()) {
            params.put("category", category);
        }
        apiService.getDocumentList(params).enqueue(new PageApiCallback<BatchDocumentDto>() {
            @Override
            public void onSuccess(long total, List<BatchDocumentDto> rows) {
                showLoading(false);
                List<Document> documents = convert(rows);
                rvDocuments.setAdapter(new DocumentAdapter(documents, document -> {
                    new AlertDialog.Builder(DocumentActivity.this)
                            .setTitle(document.getTitle())
                            .setMessage(document.getContent())
                            .setPositiveButton(R.string.confirm, null)
                            .show();
                }));
                tvEmpty.setVisibility(documents.isEmpty() ? View.VISIBLE : View.GONE);
                rvDocuments.setVisibility(documents.isEmpty() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(DocumentActivity.this, "文档加载失败：" + msg);
                rvDocuments.setAdapter(new DocumentAdapter(new ArrayList<>(), document -> {}));
                tvEmpty.setVisibility(View.VISIBLE);
                rvDocuments.setVisibility(View.GONE);
            }
        });
    }

    private void loadDocumentDetail(Long documentId) {
        showLoading(true);
        apiService.getDocumentById(documentId).enqueue(new ApiCallback<BatchDocumentDto>() {
            @Override
            public void onSuccess(BatchDocumentDto dto) {
                showLoading(false);
                if (dto == null) {
                    ToastUtil.show(DocumentActivity.this, "文档不存在");
                    loadCategories();
                    return;
                }
                new AlertDialog.Builder(DocumentActivity.this)
                        .setTitle(dto.getDocumentTitle() != null ? dto.getDocumentTitle() : "")
                        .setMessage(dto.getContent() != null ? dto.getContent() : "")
                        .setPositiveButton(R.string.confirm, (dialog, which) -> loadCategories())
                        .setOnDismissListener(dialog -> loadCategories())
                        .show();
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(DocumentActivity.this, "文档加载失败：" + msg);
                loadCategories();
            }
        });
    }

    private List<Document> convert(List<BatchDocumentDto> rows) {
        List<Document> list = new ArrayList<>();
        if (rows == null) return list;
        for (BatchDocumentDto dto : rows) {
            Document doc = new Document(
                    dto.getDocumentId() != null ? String.valueOf(dto.getDocumentId()) : "",
                    dto.getDocumentTitle(),
                    dto.getCategory(),
                    dto.getUpdateTime(),
                    dto.getContent()
            );
            list.add(doc);
        }
        return list;
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
