package com.nwu.nishat.travelguidebd;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;

public class Place implements Serializable {
    private String name;
    private String image;
    private String description;
    private GeoPoint location;

    public Place(String name, String image, String description, GeoPoint location) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.location = location;
    }

    public GeoPoint getLocation() {

        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Place(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
