package com.example.cj.videoeditor.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class AppConfig {

    public static final String SP_LOGIN = "sp_login";
    public static final String SP_KEY_IS_LOGIN = "sp_key_is_login";
    public static final String SP_KEY_USER_PHONE = "sp_key_user_phone";
    public static final String SP_KEY_USER_NAME = "sp_key_user_name";
    public static final String SP_KEY_USER_AVATAR = "sp_key_user_avatar";
    public static final String SP_KEY_USER_VIP = "sp_key_user_vip";
    public static final String SP_KEY_USER_VIP_EXPIRE = "sp_key_user_vip_expire";
    public static final String SP_KEY_COMPUTE_TOTAL = "sp_key_compute_total";
    public static final String SP_KEY_COMPUTE_USED = "sp_key_compute_used";
    public static final String SP_KEY_COMPUTE_REMAIN = "sp_key_compute_remain";

    public static final String SP_KEY_CUSTOMER_ID = "sp_key_customer_id";
    public static final String SP_KEY_CUSTOMER_TYPE = "sp_key_customer_type";
    public static final String SP_KEY_PARENT_PHONE = "sp_key_parent_phone";

    // 后端 JWT Token
    public static final String SP_KEY_USER_ID = "sp_key_user_id";
    public static final String SP_KEY_TOKEN = "sp_key_token";

    // 品牌配置缓存
    public static final String SP_KEY_BRAND_APP_LOGO = "sp_key_brand_app_logo";
    public static final String SP_KEY_BRAND_PRODUCT_NAME = "sp_key_brand_product_name";
    public static final String SP_KEY_BRAND_SLOGAN = "sp_key_brand_slogan";
    public static final String SP_KEY_BRAND_LOGIN_BG = "sp_key_brand_login_bg";
    public static final String SP_KEY_BRAND_PRIMARY_COLOR = "sp_key_brand_primary_color";

    private AppConfig() {}

    /**
     * 缓存品牌配置到 SharedPreferences。
     */
    public static void saveBrandConfig(Context context, Map<String, Object> config) {
        if (context == null || config == null) return;
        SharedPrefUtil.putString(context, SP_KEY_BRAND_APP_LOGO, getValue(config, "appLogo"));
        SharedPrefUtil.putString(context, SP_KEY_BRAND_PRODUCT_NAME, getValue(config, "productName"));
        SharedPrefUtil.putString(context, SP_KEY_BRAND_SLOGAN, getValue(config, "slogan"));
        SharedPrefUtil.putString(context, SP_KEY_BRAND_LOGIN_BG, getValue(config, "loginBg"));
        SharedPrefUtil.putString(context, SP_KEY_BRAND_PRIMARY_COLOR, getValue(config, "primaryColor"));
    }

    /**
     * 从 SharedPreferences 读取品牌配置。
     */
    public static Map<String, Object> getBrandConfig(Context context) {
        Map<String, Object> config = new HashMap<>();
        if (context == null) return config;
        config.put("appLogo", SharedPrefUtil.getString(context, SP_KEY_BRAND_APP_LOGO, ""));
        config.put("productName", SharedPrefUtil.getString(context, SP_KEY_BRAND_PRODUCT_NAME, ""));
        config.put("slogan", SharedPrefUtil.getString(context, SP_KEY_BRAND_SLOGAN, ""));
        config.put("loginBg", SharedPrefUtil.getString(context, SP_KEY_BRAND_LOGIN_BG, ""));
        config.put("primaryColor", SharedPrefUtil.getString(context, SP_KEY_BRAND_PRIMARY_COLOR, ""));
        return config;
    }

    private static String getValue(Map<String, Object> config, String key) {
        Object value = config.get(key);
        return value != null ? value.toString() : "";
    }
}
