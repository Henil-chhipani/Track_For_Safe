package com.example.trackforsafe.Modal;

public class News_feedModal {
    String title;
    String description;
    String url;
    String source;

    public News_feedModal(String title, String description, String url, String source) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
