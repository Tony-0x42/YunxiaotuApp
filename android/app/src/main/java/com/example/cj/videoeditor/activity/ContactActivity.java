package com.example.cj.videoeditor.activity;

import dagger.hilt.android.AndroidEntryPoint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.adapter.ContactAdapter;
import com.example.cj.videoeditor.bean.Contact;
import com.example.cj.videoeditor.network.ApiCallback;
import com.example.cj.videoeditor.network.ApiService;
import com.example.cj.videoeditor.network.dto.BatchContactDto;
import com.example.cj.videoeditor.utils.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@AndroidEntryPoint
public class ContactActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    private TextView tvOnlinePhone;
    private TextView tvHeadquarterPhone;
    private RecyclerView rvContacts;
    private ProgressBar progressBar;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.contact));
        tvOnlinePhone = findViewById(R.id.tv_online_phone);
        tvHeadquarterPhone = findViewById(R.id.tv_headquarter_phone);
        rvContacts = findViewById(R.id.rv_contacts);
        progressBar = findViewById(R.id.progress_bar);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        loadContacts();
    }

    private void loadContacts() {
        showLoading(true);
        apiService.getContactList().enqueue(new ApiCallback<List<BatchContactDto>>() {
            @Override
            public void onSuccess(List<BatchContactDto> data) {
                showLoading(false);
                bindContacts(data);
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(ContactActivity.this, "联系方式加载失败：" + msg);
                bindContacts(new ArrayList<>());
            }
        });
    }

    private void bindContacts(List<BatchContactDto> rows) {
        String onlinePhone = "";
        String headquarterPhone = "";
        List<Contact> regionList = new ArrayList<>();
        if (rows != null) {
            for (BatchContactDto dto : rows) {
                Integer type = dto.getContactType();
                if (type == null) continue;
                if (type == 1) {
                    onlinePhone = dto.getPhone();
                } else if (type == 2) {
                    headquarterPhone = dto.getPhone();
                } else if (type == 3) {
                    regionList.add(new Contact(
                            dto.getContactName(),
                            dto.getRegion(),
                            dto.getPhone()
                    ));
                }
            }
        }

        tvOnlinePhone.setText(!onlinePhone.isEmpty() ? onlinePhone : "暂未配置");
        tvHeadquarterPhone.setText(!headquarterPhone.isEmpty() ? headquarterPhone : "暂未配置");

        final String finalOnlinePhone = onlinePhone;
        final String finalHeadquarterPhone = headquarterPhone;
        findViewById(R.id.tv_online_phone).setOnClickListener(v -> {
            if (!finalOnlinePhone.isEmpty()) dial(finalOnlinePhone);
        });
        findViewById(R.id.tv_headquarter_phone).setOnClickListener(v -> {
            if (!finalHeadquarterPhone.isEmpty()) dial(finalHeadquarterPhone);
        });

        rvContacts.setAdapter(new ContactAdapter(regionList));
    }

    private void dial(String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(intent);
        } catch (Exception e) {
            ToastUtil.show(this, "拨号失败，请检查设备权限");
        }
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
