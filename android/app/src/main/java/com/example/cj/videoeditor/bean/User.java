package com.example.cj.videoeditor.bean;

public class User {
    public String phone;
    public String nickname;
    public String name;
    public String avatar;
    public String avatarUrl;
    public boolean vip;
    public String vipExpire;
    public int computePower;
    public int usedComputePower;
    public int totalCompute;
    public int usedCompute;

    public User() {}

    public User(String phone, String nickname, String avatar, boolean vip,
                String vipExpire, int computePower, int usedComputePower) {
        this.phone = phone;
        this.nickname = nickname;
        this.name = nickname;
        this.avatar = avatar;
        this.avatarUrl = avatar;
        this.vip = vip;
        this.vipExpire = vipExpire;
        this.computePower = computePower;
        this.usedComputePower = usedComputePower;
        this.totalCompute = computePower;
        this.usedCompute = usedComputePower;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getNickname() { return nickname == null ? name : nickname; }
    public void setNickname(String nickname) {
        this.nickname = nickname;
        this.name = nickname;
    }

    public String getName() { return name == null ? nickname : name; }
    public void setName(String name) { this.name = name; }

    public String getAvatar() { return avatar == null ? avatarUrl : avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getAvatarUrl() { return avatarUrl == null ? avatar : avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }

    public String getVipExpire() { return vipExpire; }
    public void setVipExpire(String vipExpire) { this.vipExpire = vipExpire; }

    public int getComputePower() { return computePower > 0 ? computePower : totalCompute; }
    public void setComputePower(int computePower) { this.computePower = computePower; }

    public int getUsedComputePower() { return usedComputePower > 0 ? usedComputePower : usedCompute; }
    public void setUsedComputePower(int usedComputePower) { this.usedComputePower = usedComputePower; }

    public int getTotalCompute() { return totalCompute > 0 ? totalCompute : computePower; }
    public void setTotalCompute(int totalCompute) { this.totalCompute = totalCompute; }

    public int getUsedCompute() { return usedCompute > 0 ? usedCompute : usedComputePower; }
    public void setUsedCompute(int usedCompute) { this.usedCompute = usedCompute; }
}
