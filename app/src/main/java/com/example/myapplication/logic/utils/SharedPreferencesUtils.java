package com.example.myapplication.logic.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    SharedPreferences sharedPreferences;

    public SharedPreferencesUtils(Context context,String fileName){
        sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
    }

    public static class ContentValue {
        String key;
        Object value;

        //通过构造方法来传入key和value
        public ContentValue(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public boolean putValues(ContentValue... contentValues) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (ContentValue contentValue : contentValues) {

            if (contentValue.value instanceof String) {
                boolean commit = editor.putString(contentValue.key, contentValue.value.toString()).commit();
                if(commit==false)return false;
            }
            if (contentValue.value instanceof Integer) {
                boolean commit = editor.putInt(contentValue.key, Integer.parseInt(contentValue.value.toString())).commit();
                if(commit==false)return false;
            }

            if (contentValue.value instanceof Long) {
                boolean commit = editor.putLong(contentValue.key, Long.parseLong(contentValue.value.toString())).commit();
                if(commit==false)return false;
            }
            if (contentValue.value instanceof Boolean) {
                boolean commit = editor.putBoolean(contentValue.key, Boolean.parseBoolean(contentValue.value.toString())).commit();
                if(commit==false)return false;
            }
        }
        return true;
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public boolean getBoolean(String key, Boolean b) {
        return sharedPreferences.getBoolean(key, b);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }


    public long getLong(String key) {
        return sharedPreferences.getLong(key, -1);
    }

    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

}
