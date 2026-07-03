package com.example.cj.videoeditor.bean;

public class Material {
    public String id;
    public String title;
    public Type type;
    public String coverUrl;
    public String contentUrl;
    public String publishTime;
    public int viewCount;

    public Material() {}

    public Material(String id, String title, Type type, String coverUrl,
                    String contentUrl, String publishTime, int viewCount) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.coverUrl = coverUrl;
        this.contentUrl = contentUrl;
        this.publishTime = publishTime;
        this.viewCount = viewCount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public String getContentUrl() { return contentUrl; }
    public void setContentUrl(String contentUrl) { this.contentUrl = contentUrl; }

    public String getPublishTime() { return publishTime; }
    public void setPublishTime(String publishTime) { this.publishTime = publishTime; }

    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public enum Type {
        VIDEO,
        DOCUMENT,
        PDF
    }
}
