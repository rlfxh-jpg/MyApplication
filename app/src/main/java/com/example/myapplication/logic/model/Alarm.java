package com.example.myapplication.logic.model;


public class Alarm {


    private long _id;

    private String _title;
    //备注
    private String _content;
    private int _hour, _minute;
    //-1==仅响铃一次，0==每天都响，>0==一周中响几天
    private int _dateCount;
    //代表周几
    private int[] _week = new int[7];
    //激活状态
    private int _isActivated;

    public final static int ONLY_ONCE = -1;
    public final static int EVERYDAY = 0;

    public final static int ACTIVATED = 1;
    public final static int DEACTIVATED = 0;

    public Alarm() {

    }

    public Alarm(String title, String content, int hour, int minute, int dateCount, int isActivated) {
        _title = title;
        _content = content;
        _hour = hour;
        _minute = minute;
        _dateCount = dateCount;
        _isActivated = isActivated;
    }

    public Alarm(String title, String content, int hour, int minute, int dateCount, int[] week, int isActivated) {

        _title = title;
        _content = content;
        _hour = hour;
        _minute = minute;
        _dateCount = dateCount;
        _week = week;
        _isActivated = isActivated;
    }

    public Alarm(long id,String title, String content, int hour, int minute, int dateCount, int[] week, int isActivated) {
        _id=id;
        _title = title;
        _content = content;
        _hour = hour;
        _minute = minute;
        _dateCount = dateCount;
        _week = week;
        _isActivated = isActivated;
    }


    public long getId() {
        return _id;
    }

    public String getTitle() {
        return _title;
    }

    public String getContent() {
        return _content;
    }

    public int getHour() {
        return _hour;
    }

    public int getMinute() {
        return _minute;
    }

    public int getDateCount() {
        return _dateCount;
    }

    public String getWeekInString() {
        String s = "";
        for (int i = 0; i < 7; i++) {
            s += _week[i];
        }
        return s;
    }

    public int[] getWeek() {
        return _week;
    }

    public int getIsActivated() {
        return _isActivated;
    }

    public void setId(long id) {
        _id = id;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public void setContent(String content) {
        _content = content;
    }

    public void setTime(int hour, int minute) {
        _hour = hour;
        _minute = minute;
    }

    public void setDateCount(int dateCount) {
        _dateCount = dateCount;
    }

    public void setWeek(int[] week) {
        _week = week;
    }

    public void setIsActivated(int isActivated) {
        _isActivated = isActivated;
    }
}
