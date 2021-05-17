package com.tondz.letstravel.Model;

public class News {
    private String id;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public News(String id, String title, String content, String id_places, byte[] image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.id_places = id_places;
        this.image = image;
    }

    private String content;
    private String id_places;
    private byte[]image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId_places() {
        return id_places;
    }

    public void setId_places(String id_places) {
        this.id_places = id_places;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
