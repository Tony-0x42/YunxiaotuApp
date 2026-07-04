package com.example.cj.videoeditor.bean;

public class HomeMenu {
    public String name;
    public int iconRes;
    public String iconUrl;
    public String targetType;
    public String targetValue;

    public HomeMenu() {}

    public HomeMenu(String name, int iconRes) {
        this(name, iconRes, null);
    }

    public HomeMenu(String name, int iconRes, String iconUrl) {
        this(name, iconRes, iconUrl, null, null);
    }

    public HomeMenu(String name, int iconRes, String iconUrl, String targetType, String targetValue) {
        this.name = name;
        this.iconRes = iconRes;
        this.iconUrl = iconUrl;
        this.targetType = targetType;
        this.targetValue = targetValue;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getIconRes() { return iconRes; }
    public void setIconRes(int iconRes) { this.iconRes = iconRes; }

    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }

    public String getTargetValue() { return targetValue; }
    public void setTargetValue(String targetValue) { this.targetValue = targetValue; }
}
