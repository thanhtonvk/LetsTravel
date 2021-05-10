package com.tondz.letstravel.Model;

import com.google.android.gms.maps.model.LatLng;

public class Status {
    private String id,content;
    private byte[]image;
    private LatLng latLng;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Status(String id, String content, byte[] image, LatLng latLng) {
        this.id = id;
        this.content = content;
        this.image = image;
        this.latLng = latLng;
    }
}
