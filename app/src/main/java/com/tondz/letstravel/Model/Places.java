package com.tondz.letstravel.Model;

import com.google.android.gms.maps.model.LatLng;

public class Places {
    private String id,name;
    private LatLng latLng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Places(String id, String name, LatLng latLng) {
        this.id = id;
        this.name = name;
        this.latLng = latLng;
    }
}
