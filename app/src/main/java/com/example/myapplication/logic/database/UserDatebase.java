package com.example.myapplication.logic.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.logic.dao.UserDao;
import com.example.myapplication.logic.model.UserLogin;

@Database(entities = {UserLogin.class},version = 1,exportSchema = false)
public abstract class UserDatebase extends RoomDatabase {
    public abstract UserDao userDao();
}
