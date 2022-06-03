package com.example.myapplication.logic.dao;

import androidx.room.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.myapplication.logic.model.AlarmModel;
import java.util.List;


@Dao
public interface AlarmDao {

    @Insert
    void insertAll(AlarmModel... alarmModels);

    @Delete
    void Delete(AlarmModel alarmModel);

    @Query("SELECT * FROM AlarmModel")
    List<AlarmModel> getAll();

    @Update
    void Updata(AlarmModel alarmModel);

    @Query("SELECT * FROM AlarmModel WHERE _id =:id")
    AlarmModel Getone(long id);

}
