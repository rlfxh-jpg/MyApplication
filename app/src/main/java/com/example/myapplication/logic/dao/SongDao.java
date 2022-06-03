package com.example.myapplication.logic.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.logic.model.Song;

import java.util.List;

@Dao
public interface SongDao {

    @Insert
    void insertAll(Song... song);

    @Query("SELECT * FROM Song")
    List<Song> getAll();

}
