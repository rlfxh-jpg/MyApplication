package com.example.myapplication.logic.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "AlarmModel")
public class AlarmModel {

    @PrimaryKey(autoGenerate = true)
    private long _id;

    private String _title;
    //备注
    private String _content;

    private int _hour, _minute;
    //-1==仅响铃一次，0==每天都响，>0==一周中响几天
    private int _dateCount;

    //代表周几
    private String _week;
    //激活状态
    private int _isActivated;
    @Ignore
    public AlarmModel(){

    }
    public AlarmModel(String _title, String _content, int _hour, int _minute, int _dateCount, String _week, int _isActivated) {
        this._title = _title;
        this._content = _content;
        this._hour = _hour;
        this._minute = _minute;
        this._dateCount = _dateCount;
        this._week = _week;
        this._isActivated = _isActivated;
    }




    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public int get_hour() {
        return _hour;
    }

    public void set_hour(int _hour) {
        this._hour = _hour;
    }

    public int get_minute() {
        return _minute;
    }

    public void set_minute(int _minute) {
        this._minute = _minute;
    }

    public int get_dateCount() {
        return _dateCount;
    }

    public void set_dateCount(int _dateCount) {
        this._dateCount = _dateCount;
    }

    public String get_week() {
        return _week;
    }

    public void set_week(String _week) {
        this._week = _week;
    }

    public int get_isActivated() {
        return _isActivated;
    }

    public void set_isActivated(int _isActivated) {
        this._isActivated = _isActivated;
    }
}
