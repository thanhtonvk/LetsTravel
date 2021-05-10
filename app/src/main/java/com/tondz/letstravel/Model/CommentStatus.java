package com.tondz.letstravel.Model;

public class CommentStatus {
    private String id,id_status,username,content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_status() {
        return id_status;
    }

    public void setId_status(String id_status) {
        this.id_status = id_status;
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

    public CommentStatus(String id, String id_status, String username, String content) {
        this.id = id;
        this.id_status = id_status;
        this.username = username;
        this.content = content;
    }
}
