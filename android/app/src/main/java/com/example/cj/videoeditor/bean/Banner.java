package com.example.cj.videoeditor.bean;

public class Banner {
    public String id;
    public String imageUrl;
    public String link;
    public int sort;
    public String title;

    public Banner(String id, String imageUrl, String link, int sort) {
        this(id, imageUrl, link, sort, "");
    }

    public Banner(String id, String imageUrl, String link, int sort, String title) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.link = link;
        this.sort = sort;
        this.title = title;
    }

    public String getId() { return id; }
    public String getImageUrl() { return imageUrl; }
    public String getLink() { return link; }
    public int getSort() { return sort; }
    public String getTitle() { return title; }
}