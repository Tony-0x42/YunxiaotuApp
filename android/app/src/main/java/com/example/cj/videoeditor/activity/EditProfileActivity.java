package com.example.cj.videoeditor.activity;

import dagger.hilt.android.AndroidEntryPoint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.bean.User;
import com.example.cj.videoeditor.network.ApiCallback;
import com.example.cj.videoeditor.network.ApiService;
import com.example.cj.videoeditor.network.dto.BatchCustomerDto;
import com.example.cj.videoeditor.utils.AppConfig;
import com.example.cj.videoeditor.utils.SharedPrefUtil;
import com.example.cj.videoeditor.utils.ToastUtil;
import com.example.cj.videoeditor.utils.UserStore;

import javax.inject.Inject;

@AndroidEntryPoint
public class EditProfileActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    private ImageView ivAvatar;
    private EditText etNickname;
    private EditText etPhone;
    private Button btnSave;
    private ProgressBar progressBar;

    private User currentUser;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.edit_profile));
        ivAvatar = findViewById(R.id.iv_avatar);
        etNickname = findViewById(R.id.et_nickname);
        etPhone = findViewById(R.id.et_phone);
        btnSave = findViewById(R.id.btn_save);
        progressBar = findViewById(R.id.progress_bar);

        currentUser = UserStore.getUser(this);
        if (currentUser == null) {
            currentUser = new User();
            currentUser.setPhone(SharedPrefUtil.getString(this, AppConfig.SP_KEY_USER_PHONE, ""));
            currentUser.setNickname(SharedPrefUtil.getString(this, AppConfig.SP_KEY_USER_NAME, ""));
            currentUser.setAvatar(SharedPrefUtil.getString(this, AppConfig.SP_KEY_USER_AVATAR, ""));
        }
        etNickname.setText(currentUser.getNickname());
        etPhone.setText(currentUser.getPhone());

        ivAvatar.setOnClickListener(v -> ToastUtil.show(this, "头像上传功能开发中"));
        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String nickname = etNickname.getText() == null ? "" : etNickname.getText().toString().trim();
        if (nickname.isEmpty()) {
            ToastUtil.show(this, "请输入昵称");
            return;
        }
        showLoading(true);

        BatchCustomerDto dto = new BatchCustomerDto();
        dto.setCustomerName(nickname);
        dto.setContactName(nickname);

        apiService.updateAppCustomer(dto).enqueue(new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object data) {
                showLoading(false);
                currentUser.setNickname(nickname);
                UserStore.saveUser(EditProfileActivity.this, currentUser);
                SharedPrefUtil.putString(EditProfileActivity.this, AppConfig.SP_KEY_USER_NAME, nickname);
                ToastUtil.show(EditProfileActivity.this, "保存成功");
                finish();
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(EditProfileActivity.this, msg);
            }
        });
    }

    private void showLoading(boolean show) {
        if (progressBar != null) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        btnSave.setEnabled(!show);
    }
}
