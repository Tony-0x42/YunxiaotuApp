package com.example.cj.videoeditor.utils;

import android.content.Context;

import com.example.cj.videoeditor.bean.User;

public class UserSession {

    private UserSession() {}

    public static void saveLogin(Context context, User user) {
        if (user == null) return;
        SharedPrefUtil.putBoolean(context, AppConfig.SP_KEY_IS_LOGIN, true);
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_USER_PHONE, user.getPhone());
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_USER_NAME, user.getNickname());
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_USER_AVATAR, user.getAvatar());
        SharedPrefUtil.putBoolean(context, AppConfig.SP_KEY_USER_VIP, user.isVip());
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_USER_VIP_EXPIRE, user.getVipExpire());
        SharedPrefUtil.putInt(context, AppConfig.SP_KEY_COMPUTE_TOTAL, user.getComputePower() > 0 ? user.getComputePower() : 1000);
        SharedPrefUtil.putInt(context, AppConfig.SP_KEY_COMPUTE_USED, user.getUsedComputePower());
    }

    public static boolean isLogin(Context context) {
        return SharedPrefUtil.getBoolean(context, AppConfig.SP_KEY_IS_LOGIN, false);
    }

    public static void logout(Context context) {
        SharedPrefUtil.clear(context);
    }
}
