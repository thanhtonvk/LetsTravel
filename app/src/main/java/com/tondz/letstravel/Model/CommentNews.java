package com.tondz.letstravel.Model;

public class CommentNews {
    private String id,id_news,username,content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_news() {
        return id_news;
    }

    public void setId_news(String id_news) {
        this.id_news = id_news;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentNews(String id, String id_news, String username, String content) {
        this.id = id;
        this.id_news = id_news;
        this.username = username;
        this.content = content;
    }
}
