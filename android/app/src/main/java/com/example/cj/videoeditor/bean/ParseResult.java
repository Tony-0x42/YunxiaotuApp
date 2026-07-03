package com.example.cj.videoeditor.bean;

import java.util.List;

public class ParseResult {
    public String videoUrl;
    public List<String> images;
    public String text;

    public ParseResult() {}

    public ParseResult(String videoUrl, List<String> images, String text) {
        this.videoUrl = videoUrl;
        this.images = images;
        this.text = text;
    }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
