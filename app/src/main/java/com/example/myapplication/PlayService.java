package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class PlayService extends Service {
    private static MediaPlayer mediaPlayer;
    private MsBinder msBinder=new MsBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=new MediaPlayer();
    }

    public class MsBinder extends Binder{

        public void PlayAudio(String uri){
            Log.e("uri",uri);
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(getApplication(), Uri.parse(uri));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public int ChangeStatus(){
            if(mediaPlayer==null)return -1;
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                return 1;
            }else {
                mediaPlayer.start();
                return 2;
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return msBinder;
    }
}