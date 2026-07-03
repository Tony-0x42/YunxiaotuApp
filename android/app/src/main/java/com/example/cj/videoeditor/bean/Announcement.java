package com.example.cj.videoeditor.bean;

public class Announcement {
    public String title;
    public String championName;
    public String amount;

    public Announcement(String title, String championName, String amount) {
        this.title = title;
        this.championName = championName;
        this.amount = amount;
    }

    public String getTitle() { return title; }
    public String getChampionName() { return championName; }
    public String getAmount() { return amount; }
}