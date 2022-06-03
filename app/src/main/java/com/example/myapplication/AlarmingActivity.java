package com.example.myapplication;

import android.app.Service;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarming);
        String message = this.getIntent().getStringExtra("msg");
        int flag = this.getIntent().getIntExtra("flag", 0);
        showDialogInBroadcastReceiver(message, flag);
    }

    private void showDialogInBroadcastReceiver(String message, final int flag) {
        if (flag == 1 || flag == 2) {
            mediaPlayer = MediaPlayer.create(this, R.raw.calling);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        if (flag == 0 || flag == 2) {
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            assert vibrator != null;
            vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);
        }

        Calendar c=Calendar.getInstance();
        String s=c.get(Calendar.HOUR_OF_DAY)+"："+c.get(Calendar.MINUTE);
        final SimpleDialog dialog = new SimpleDialog(this);
        dialog.show();
        dialog.setTitle(s);
        dialog.setMessage("");
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {
                    if (flag == 1 || flag == 2) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    if (flag == 0 || flag == 2) {
                        vibrator.cancel();
                    }
                    dialog.dismiss();
                    finish();
                }
            }
        });
    }
}