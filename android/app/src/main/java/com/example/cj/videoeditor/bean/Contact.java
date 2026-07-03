package com.example.cj.videoeditor.bean;

public class Contact {
    public String name;
    public String region;
    public String phone;
    public String type;

    public Contact() {}

    public Contact(String name, String region, String phone) {
        this.name = name;
        this.region = region;
        this.phone = phone;
    }

    public Contact(String name, String region, String phone, String type) {
        this.name = name;
        this.region = region;
        this.phone = phone;
        this.type = type;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
