package com.example.myapplication.logic.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.logic.model.UserLogin;

@Dao
public interface UserDao {

    @Query("select * from UserLogin where name = :name")
    UserLogin selectOne(String name);

    @Insert
    void insertAll(UserLogin... userLogins);

}
