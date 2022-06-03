package com.example.myapplication.logic.model;

/**
 * simple pojo class for making book
 * objects used in filling up the
 * listview of SearchBooksActivity
 */
public class BookListView {
    private String title;
    private String imageUrl;
    private String link;

    //constructor of the class
    public BookListView(String title, String imageUrl, String link) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    public String getTitle() {return title; }

    public void setTitle(String title) {this.title = title;}

    public String getImageUrl() {return imageUrl;}

    public String getLink() { return link;}

    public void setLink(String link) { this.link = link;}

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
}
