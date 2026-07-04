package com.example.cj.videoeditor.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.bumptech.glide.Glide;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.bean.User;
import com.example.cj.videoeditor.network.ApiCallback;
import com.example.cj.videoeditor.network.ApiService;
import com.example.cj.videoeditor.network.dto.BatchCustomerDto;
import com.example.cj.videoeditor.utils.AppConfig;
import com.example.cj.videoeditor.utils.SharedPrefUtil;
import com.example.cj.videoeditor.utils.ToastUtil;
import com.example.cj.videoeditor.utils.UserStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        uploadAvatar(selectedImageUri);
                    }
                }
            }
    );

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
        loadAvatar(currentUser.getAvatar());

        ivAvatar.setOnClickListener(v -> pickAvatar());
        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void loadAvatar(String url) {
        if (url == null || url.isEmpty()) {
            ivAvatar.setImageResource(R.drawable.ic_avatar_placeholder);
            return;
        }
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .error(R.drawable.ic_avatar_placeholder)
                .circleCrop()
                .into(ivAvatar);
    }

    private void pickAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private void uploadAvatar(Uri uri) {
        File compressedFile = compressImage(uri);
        if (compressedFile == null) {
            ToastUtil.show(this, "图片处理失败，请重试");
            return;
        }

        showLoading(true);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), compressedFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", compressedFile.getName(), requestFile);

        apiService.uploadAvatar(body).enqueue(new ApiCallback<String>() {
            @Override
            public void onSuccess(String avatarUrl) {
                if (avatarUrl == null || avatarUrl.isEmpty()) {
                    showLoading(false);
                    ToastUtil.show(EditProfileActivity.this, "头像上传失败，请重试");
                    return;
                }
                updateAvatarUrl(avatarUrl);
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(EditProfileActivity.this, msg);
            }
        });
    }

    private void updateAvatarUrl(String avatarUrl) {
        BatchCustomerDto dto = new BatchCustomerDto();
        dto.setCustomerName(etNickname.getText() == null ? "" : etNickname.getText().toString().trim());
        dto.setContactName(dto.getCustomerName());
        dto.setAvatarUrl(avatarUrl);

        apiService.updateAppCustomer(dto).enqueue(new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object data) {
                showLoading(false);
                currentUser.setAvatar(avatarUrl);
                currentUser.setAvatarUrl(avatarUrl);
                UserStore.saveUser(EditProfileActivity.this, currentUser);
                SharedPrefUtil.putString(EditProfileActivity.this, AppConfig.SP_KEY_USER_AVATAR, avatarUrl);
                loadAvatar(avatarUrl);
                ToastUtil.show(EditProfileActivity.this, "头像更新成功");
            }

            @Override
            public void onError(String msg) {
                showLoading(false);
                ToastUtil.show(EditProfileActivity.this, msg);
            }
        });
    }

    private File compressImage(Uri uri) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            try (InputStream is = getContentResolver().openInputStream(uri)) {
                BitmapFactory.decodeStream(is, null, options);
            }

            int maxSize = 512;
            int scale = 1;
            while (options.outWidth / scale > maxSize || options.outHeight / scale > maxSize) {
                scale *= 2;
            }

            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            Bitmap bitmap;
            try (InputStream is = getContentResolver().openInputStream(uri)) {
                bitmap = BitmapFactory.decodeStream(is, null, options);
            }
            if (bitmap == null) {
                return null;
            }

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width > maxSize || height > maxSize) {
                float ratio = Math.min((float) maxSize / width, (float) maxSize / height);
                width = Math.round(width * ratio);
                height = Math.round(height * ratio);
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, width, height, true);
                if (scaled != bitmap) {
                    bitmap.recycle();
                }
                bitmap = scaled;
            }

            File cacheDir = new File(getCacheDir(), "avatars");
            if (!cacheDir.exists() && !cacheDir.mkdirs()) {
                return null;
            }
            File outFile = new File(cacheDir, "avatar_" + System.currentTimeMillis() + ".jpg");
            try (FileOutputStream fos = new FileOutputStream(outFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            }
            bitmap.recycle();
            return outFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
