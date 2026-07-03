package com.example.cj.videoeditor.bean;

public class VideoClip {
    public String id;
    public String videoUrl;
    public String text;
    public double duration;

    public VideoClip() {}

    public VideoClip(String id, String videoUrl, String text, double duration) {
        this.id = id;
        this.videoUrl = videoUrl;
        this.text = text;
        this.duration = duration;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }
}
