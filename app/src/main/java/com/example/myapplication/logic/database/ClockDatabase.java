package com.example.myapplication.logic.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.logic.dao.ClockDao;
import com.example.myapplication.logic.model.ClockDate;

@Database(entities = {ClockDate.class},version = 1,exportSchema = false)
public abstract class ClockDatabase extends RoomDatabase {
    public abstract ClockDao clockDao();

}
