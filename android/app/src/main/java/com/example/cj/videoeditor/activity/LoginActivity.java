package com.example.cj.videoeditor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cj.videoeditor.MainActivity;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.network.ApiService;
import com.example.cj.videoeditor.network.AuthApiCallback;
import com.example.cj.videoeditor.network.dto.AppLoginBody;
import com.example.cj.videoeditor.network.dto.BatchCustomerDto;
import com.example.cj.videoeditor.network.util.TokenManager;
import com.example.cj.videoeditor.utils.PhoneUtil;
import com.example.cj.videoeditor.utils.ToastUtil;
import com.example.cj.videoeditor.utils.UserSession;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    @Inject
    ApiService apiService;

    @Inject
    TokenManager tokenManager;

    private EditText etPhone, etPassword;
    private CheckBox cbAgreement;
    private Button btnLogin;
    private TextView tvAgreement, tvPrivacy, tvGoRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        cbAgreement = findViewById(R.id.cb_agreement);
        btnLogin = findViewById(R.id.btn_login);
        tvAgreement = findViewById(R.id.tv_user_agreement);
        tvPrivacy = findViewById(R.id.tv_privacy);
        tvGoRegister = findViewById(R.id.tv_go_register);
        progressBar = findViewById(R.id.progress_bar);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                updateLoginButton();
            }
        };
        etPhone.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);
        cbAgreement.setOnCheckedChangeListener((buttonView, isChecked) -> updateLoginButton());

        btnLogin.setOnClickListener(v -> attemptLogin());
        tvAgreement.setOnClickListener(v -> startActivity(new Intent(this, AgreementActivity.class)));
        tvPrivacy.setOnClickListener(v -> startActivity(new Intent(this, PrivacyActivity.class)));
        tvGoRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void updateLoginButton() {
        boolean enabled = PhoneUtil.isValidPhone(etPhone.getText().toString().trim())
                && etPassword.getText().toString().trim().length() >= 6
                && cbAgreement.isChecked();
        btnLogin.setEnabled(enabled);
        btnLogin.setBackgroundResource(enabled ? R.drawable.bg_button_blue : R.drawable.bg_button_disabled);
    }

    private void attemptLogin() {
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (!PhoneUtil.isValidPhone(phone)) {
            ToastUtil.show(this, R.string.phone_format_error);
            return;
        }
        if (password.length() < 6 || password.length() > 20) {
            ToastUtil.show(this, R.string.password_length_error);
            return;
        }
        if (!cbAgreement.isChecked()) {
            ToastUtil.show(this, R.string.agreement_required);
            return;
        }

        showLoading(true);
        apiService.appLogin(new AppLoginBody(phone, password))
                .enqueue(new AuthApiCallback<BatchCustomerDto>() {
                    @Override
                    public void onSuccess(String token, BatchCustomerDto data) {
                        showLoading(false);
                        tokenManager.saveToken(token);
                        UserSession.saveLogin(LoginActivity.this, data);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        showLoading(false);
                        ToastUtil.show(LoginActivity.this, msg);
                    }
                });
    }

    private void showLoading(boolean show) {
        btnLogin.setEnabled(!show);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            btnLogin.setText(R.string.loading);
        } else {
            btnLogin.setText(R.string.login);
            updateLoginButton();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("确定退出应用？")
                .setPositiveButton(R.string.confirm, (dialog, which) -> finish())
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
