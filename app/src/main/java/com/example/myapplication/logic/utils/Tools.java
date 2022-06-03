package com.example.myapplication.logic.utils;


import android.content.Context;

import androidx.room.Room;

import com.example.myapplication.logic.dao.AlarmDao;
import com.example.myapplication.logic.dao.ClockDao;
import com.example.myapplication.logic.dao.SongDao;
import com.example.myapplication.logic.dao.UserDao;
import com.example.myapplication.logic.dao.WakeupDao;
import com.example.myapplication.logic.database.AppDatabase;
import com.example.myapplication.logic.database.ClockDatabase;
import com.example.myapplication.logic.database.SongDatabase;
import com.example.myapplication.logic.database.UserDatebase;
import com.example.myapplication.logic.database.WakeupDatebase;
import com.example.myapplication.logic.model.Alarm;
import com.example.myapplication.logic.model.AlarmModel;

public  class Tools {

    public static Alarm ModelToAlarm(AlarmModel alarmModel){
        String content = alarmModel.get_content();
        int dateCount = alarmModel.get_dateCount();
        int hour = alarmModel.get_hour();
        int minute = alarmModel.get_minute();
        int isActivated = alarmModel.get_isActivated();
        String week = alarmModel.get_week();
        String title = alarmModel.get_title();
        if(dateCount==0||dateCount==-1)return new Alarm(alarmModel.get_id(),title,content,hour,minute,dateCount,new int[7],isActivated);
        int[] ints = stringToInt(week);
        return new Alarm(alarmModel.get_id(),title,content,hour,minute,dateCount,ints,isActivated);
    }




    public static AlarmModel AlarmToModel(Alarm alarm){
        String content = alarm.getContent();
        int dateCount = alarm.getDateCount();
        int hour = alarm.getHour();
        int minute = alarm.getMinute();
        String title = alarm.getTitle();
        int isActivated = alarm.getIsActivated();
        int[] week = alarm.getWeek();
        return new AlarmModel(title,content,hour,minute,dateCount,getWeekInString(week),isActivated);
    }

    public static AlarmModel AlarmToModelplus(Alarm alarm){
        AlarmModel alarmModel = AlarmToModel(alarm);
        alarmModel.set_id(alarm.getId());
        return alarmModel;
    }

    private static int[] stringToInt(String s) {
        int[] res = new int[7];
        if(s==null)return res;
        for (int i = 0; i < 7; i++) {
            res[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }
        return res;
    }

    public static AlarmDao GetAlarmDao(Context fragmentActivity){
        AppDatabase alarm_database = Room.databaseBuilder(fragmentActivity, AppDatabase.class, "Alarm_database").allowMainThreadQueries().build();
        AlarmDao alarmDao=alarm_database.alarmDao();
        return alarmDao;
    }
    public static SongDao SongDao(Context context){
        SongDatabase songDatabase = Room.databaseBuilder(context, SongDatabase.class, "song").allowMainThreadQueries().build();
        SongDao songDao = songDatabase.songDao();
        return songDao;
    }
    public static ClockDao clockDao(Context context){
        ClockDatabase clockDatabase = Room.databaseBuilder(context, ClockDatabase.class, "clock2").allowMainThreadQueries().build();
        ClockDao clockDao = clockDatabase.clockDao();
        return clockDao;
    }

    public static String getWeekInString(int[] _week) {
        String s = "";
        for (int i = 0; i < 7; i++) {
            s += _week[i];
        }
        return s;
    }

    public static WakeupDao wakeupDao(Context context){
        WakeupDatebase wakeupDatebase=Room.databaseBuilder(context,WakeupDatebase.class,"wakeup").allowMainThreadQueries().build();
        WakeupDao wakeupDao=wakeupDatebase.wakeupDao();
        return wakeupDao;
    }
    public static UserDao GetUserDao(Context context){
        UserDatebase userDatebase=Room.databaseBuilder(context,UserDatebase.class,"UserDaobase").allowMainThreadQueries().build();
        UserDao userDao = userDatebase.userDao();
        return userDao;
    }

}
