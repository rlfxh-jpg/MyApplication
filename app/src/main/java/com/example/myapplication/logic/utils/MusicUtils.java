package com.example.myapplication.logic.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.myapplication.logic.model.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicUtils {
    public static List<Song> getMusicData(Context context) {
        List<Song> list = new ArrayList<Song>();
        // 媒体库查询语句（写一个工具类MusicUtils）
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.Media.IS_MUSIC);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Song song = new Song();
                //歌曲名称
                song.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌手
                song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲路径
                song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲时长
                song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    if (song.song.contains("-")) {
                        String[] str = song.song.split("-");
                        song.singer = str[0];
                        song.song = str[1];
                    }
                    list.add(song);
                }
            }
            // 释放资源
            cursor.close();
        return list;
    }

    public static List<Song> getMusicData2(Context context){
        List<Song> list=new ArrayList<>();
        Cursor cursor=context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,null,null,MediaStore.Audio.AudioColumns.IS_MUSIC);
        if(cursor!=null){
            while (cursor.moveToNext()){
                Song song=new Song();

                //替换音乐名称的后缀.mp3
                String s=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                song.setSong(s.replace(".mp3",""));
                song.setSinger(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                song.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                song.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                //分离出歌曲名和歌手。因为本地媒体库读取的歌曲信息不规范
                if(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))>1000*60){
                    if(song.getSong().contains("-")){
                        String[] str=song.getSong().split("-");
                        song.setSong(str[0]);
                        song.setSinger(str[1]);
                    }
                    list.add(song);
                }
            }
            cursor.close();
        }
        return list;
    }
}
