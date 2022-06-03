package com.example.myapplication.logic.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.logic.dao.SongDao;
import com.example.myapplication.logic.model.Song;

@Database(entities = {Song.class},version = 1,exportSchema = false)
public abstract class SongDatabase extends RoomDatabase {
    public abstract SongDao songDao();

}
