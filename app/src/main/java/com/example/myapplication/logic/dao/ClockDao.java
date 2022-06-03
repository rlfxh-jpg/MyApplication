package com.example.myapplication.logic.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.logic.model.ClockDate;

import java.util.List;

@Dao
public interface ClockDao {

    @Insert
    void insertClock(ClockDate... clockDates);

    @Query("select * from ClockDate")
    List<ClockDate> getAll();

    @Update
    int Update(ClockDate clockDate);

    @Query("select * from ClockDate where year=:years and month=:month and day=:day")
    ClockDate GetOne(int years,int month,int day);

    @Delete
    void delete(ClockDate clockDates);

}
