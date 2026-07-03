package com.example.cj.videoeditor.utils;

import android.content.Context;

import com.example.cj.videoeditor.bean.User;

public class UserStore {

    private UserStore() {}

    public static void saveUser(Context context, User user) {
        if (user == null) return;
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_USER_PHONE, user.getPhone());
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_USER_NAME, user.getNickname());
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_USER_AVATAR, user.getAvatar());
        SharedPrefUtil.putBoolean(context, AppConfig.SP_KEY_USER_VIP, user.isVip());
        SharedPrefUtil.putString(context, AppConfig.SP_KEY_USER_VIP_EXPIRE, user.getVipExpire());
        SharedPrefUtil.putInt(context, AppConfig.SP_KEY_COMPUTE_TOTAL, user.getComputePower());
        SharedPrefUtil.putInt(context, AppConfig.SP_KEY_COMPUTE_USED, user.getUsedComputePower());
    }

    public static User getUser(Context context) {
        User user = new User();
        user.setPhone(SharedPrefUtil.getString(context, AppConfig.SP_KEY_USER_PHONE, ""));
        user.setNickname(SharedPrefUtil.getString(context, AppConfig.SP_KEY_USER_NAME, ""));
        user.setAvatar(SharedPrefUtil.getString(context, AppConfig.SP_KEY_USER_AVATAR, ""));
        user.setVip(SharedPrefUtil.getBoolean(context, AppConfig.SP_KEY_USER_VIP, false));
        user.setVipExpire(SharedPrefUtil.getString(context, AppConfig.SP_KEY_USER_VIP_EXPIRE, ""));
        user.setComputePower(SharedPrefUtil.getInt(context, AppConfig.SP_KEY_COMPUTE_TOTAL, 1000));
        user.setUsedComputePower(SharedPrefUtil.getInt(context, AppConfig.SP_KEY_COMPUTE_USED, 356));
        if (user.getPhone().isEmpty()) {
            return null;
        }
        return user;
    }

    public static void clear(Context context) {
        SharedPrefUtil.clear(context);
    }
}
