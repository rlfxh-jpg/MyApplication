package com.example.myapplication.logic.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.logic.dao.WakeupDao;
import com.example.myapplication.logic.model.AlarmModel;

@Database(entities = {AlarmModel.class},version = 1,exportSchema = false)
public abstract class WakeupDatebase extends RoomDatabase {
    public abstract WakeupDao wakeupDao();
}
