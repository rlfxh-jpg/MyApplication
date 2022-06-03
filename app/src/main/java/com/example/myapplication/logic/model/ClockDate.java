package com.example.myapplication.logic.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "ClockDate",primaryKeys = {"year","month","day"})
public class ClockDate {

    int year;
    int month;
    int day;

    @ColumnInfo(defaultValue = "0")
    long morning;

    @ColumnInfo(defaultValue = "0")
    long evening;

    @Override
    public String  toString() {
        return "ClockDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", morning=" + morning +
                ", evening=" + evening +
                ", mornhour=" + mornhour +
                ", mornminte=" + mornminte +
                ", evenhour=" + evenhour +
                ", evenminte=" + evenminte +
                '}';
    }

    @ColumnInfo(defaultValue = "-1")
    int mornhour;

    @ColumnInfo(defaultValue = "-1")
    int mornminte;

    @ColumnInfo(defaultValue = "-1")
    int evenhour;

    @ColumnInfo(defaultValue = "-1")
    int evenminte;

    public int getMornhour() {
        return mornhour;
    }

    public void setMornhour(int mornhour) {
        this.mornhour = mornhour;
    }

    public int getMornminte() {
        return mornminte;
    }

    public void setMornminte(int mornminte) {
        this.mornminte = mornminte;
    }

    public int getEvenhour() {
        return evenhour;
    }

    public void setEvenhour(int evenhour) {
        this.evenhour = evenhour;
    }

    public int getEvenminte() {
        return evenminte;
    }

    public void setEvenminte(int evenminte) {
        this.evenminte = evenminte;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getMorning() {
        return morning;
    }

    public void setMorning(long morning) {
        this.morning = morning;
    }

    public long getEvening() {
        return evening;
    }

    public void setEvening(long evening) {
        this.evening = evening;
    }
}
