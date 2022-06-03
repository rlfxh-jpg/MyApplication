package com.example.myapplication.logic.model;

public class OverWatch {

    private String name;
    private int imageID;
    private String uri;

    public OverWatch(String name, int imageID, String uri) {
        this.name = name;
        this.imageID = imageID;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
