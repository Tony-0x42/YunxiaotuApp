package com.example.cj.videoeditor.utils;

public class PhoneUtil {

    private PhoneUtil() {}

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.length() != 11) return false;
        return phone.startsWith("1");
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() != 11) return phone;
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}
