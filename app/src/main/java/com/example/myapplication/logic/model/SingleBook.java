package com.example.myapplication.logic.model;

/**
 * simple pojo class for making book
 * objects used in filling up the
 * listview of FavouritesActivity
 */
public class SingleBook {

    private String bookChapter;

    public SingleBook(String chapters) {
        this.bookChapter = chapters;
    }

    public String getChapters() {
        return bookChapter;
    }

    public void setChapters(String chapters) {
        this.bookChapter= chapters;
    }
}
