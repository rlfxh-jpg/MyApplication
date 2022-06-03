package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.myapplication.logic.dao.AlarmDao;
import com.example.myapplication.logic.database.AppDatabase;
import com.example.myapplication.logic.model.Alarm;
import com.example.myapplication.logic.model.AlarmModel;
import com.example.myapplication.logic.model.Data;
import com.example.myapplication.logic.utils.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.togglebuttongroup.button.ToggleButton;

import net.cachapa.expandablelayout.ExpandableLayout;

public class AlarmReSettingsActivity extends AppCompatActivity {

    private int hour;
    private int minute;

    ToggleButton mon;
    ToggleButton tue;
    ToggleButton wed;
    ToggleButton thu;
    ToggleButton fri;
    ToggleButton sat;
    ToggleButton sun;
    AlarmDao alarmDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);

        Intent intent = getIntent();
        AppDatabase appDatabase= Room.databaseBuilder(getApplication(),AppDatabase.class, "Alarm_database").allowMainThreadQueries().build();
        alarmDao = appDatabase.alarmDao();
        AlarmModel alarmModel=alarmDao.Getone(intent.getLongExtra("id",0));
        Alarm alarm=Tools.ModelToAlarm(alarmModel);
        long id = intent.getLongExtra("id", 0);
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);
        sun = findViewById(R.id.sun);

        TimePicker timePicker = findViewById(R.id.time_picker);
        Switch repeat = findViewById(R.id.repeat);
        ExpandableLayout repeatEx = findViewById(R.id.repeat_ex);
        RadioButton everyday = findViewById(R.id.everyday);
        RadioButton weekday = findViewById(R.id.weekday);
        ExpandableLayout weekEx = findViewById(R.id.week_ex);
        ToggleButton mon = findViewById(R.id.mon);
        ToggleButton tue = findViewById(R.id.tue);
        ToggleButton wed = findViewById(R.id.wed);
        ToggleButton thu = findViewById(R.id.thu);
        ToggleButton fri = findViewById(R.id.fri);
        ToggleButton sat = findViewById(R.id.sat);
        ToggleButton sun = findViewById(R.id.sun);
        EditText editText = findViewById(R.id.edit_view);
        FloatingActionButton fab = findViewById(R.id.confirm_fab);

        timePicker.setHour(alarm.getHour());
        timePicker.setMinute(alarm.getMinute());
        hour = alarm.getHour();
        minute = alarm.getMinute();
        if (alarm.getDateCount() == -1) {
            repeat.setChecked(false);
            repeatEx.collapse();
        } else if (alarm.getDateCount() == 0) {
            repeat.setChecked(true);
            repeatEx.expand();
            everyday.setChecked(true);
            weekEx.collapse();
        } else {
            repeat.setChecked(true);
            repeatEx.expand();
            weekday.setChecked(true);
            weekEx.expand();
            int[] tmp = alarm.getWeek();
            if (tmp[0] == 1)
                mon.setChecked(true);
            if (tmp[1] == 1)
                tue.setChecked(true);
            if (tmp[2] == 1)
                wed.setChecked(true);
            if (tmp[3] == 1)
                thu.setChecked(true);
            if (tmp[4] == 1)
                fri.setChecked(true);
            if (tmp[5] == 1)
                sat.setChecked(true);
            if (tmp[6] == 1)
                sun.setChecked(true);
        }
        editText.setText(alarm.getContent());

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
            }
        });


        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    repeatEx.expand();
                } else {
                    repeatEx.collapse();
                }
            }
        });


        everyday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        weekday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    weekEx.expand();
                } else {
                    weekEx.collapse();
                }
            }
        });

        fab.setOnClickListener(view -> {
            if (repeat.isChecked()) {
                if (everyday.isChecked()) {
                    Alarm newAlarm = new Alarm(makeTitle(hour, minute), editText.getText().toString(), hour, minute, Alarm.EVERYDAY, Alarm.DEACTIVATED);
                    newAlarm.setId(id);
                    alarmDao.Updata(Tools.AlarmToModelplus(newAlarm));
                } else if (weekday.isChecked()) {
                    Data data;
                    data = checkWeekStats();

                    if (data.dataCount == 0) {
                        Alarm newAlarm = new Alarm(makeTitle(hour, minute), editText.getText().toString(), hour, minute, Alarm.ONLY_ONCE, Alarm.DEACTIVATED);
                        newAlarm.setId(id);
                        alarmDao.Updata(Tools.AlarmToModelplus(newAlarm));
                    } else {
                        Alarm newAlarm = new Alarm(makeTitle(hour, minute), editText.getText().toString(), hour, minute, data.dataCount, data.data, Alarm.DEACTIVATED);
                        newAlarm.setId(id);
                        alarmDao.Updata(Tools.AlarmToModelplus(newAlarm));
                    }
                }
            } else {
                Alarm newAlarm = new Alarm(makeTitle(hour, minute), editText.getText().toString(), hour, minute, Alarm.ONLY_ONCE, Alarm.DEACTIVATED);
                newAlarm.setId(id);
                alarmDao.Updata(Tools.AlarmToModelplus(newAlarm));
            }
            finish();
        });
    }

    private String makeTitle(int hour, int minute) {
        if (hour < 10) {
            if (minute < 10) {
                return "0" + hour + "：" + "0" + minute;
            } else {
                return "0" + hour + "：" + minute;
            }
        } else {
            if (minute < 10) {
                return hour + "：" + "0" + minute;
            } else {
                return hour + "：" + minute;
            }
        }

    }

    private Data checkWeekStats() {
        Data data = new Data();
        data.dataCount = 0;

        if (mon.isChecked()) {
            data.dataCount++;
            data.data[0]++;
        }
        if (tue.isChecked()) {
            data.dataCount++;
            data.data[1]++;
        }
        if (wed.isChecked()) {
            data.dataCount++;
            data.data[2]++;
        }
        if (thu.isChecked()) {
            data.dataCount++;
            data.data[3]++;
        }
        if (fri.isChecked()) {
            data.dataCount++;
            data.data[4]++;
        }
        if (sat.isChecked()) {
            data.dataCount++;
            data.data[5]++;
        }
        if (sun.isChecked()) {
            data.dataCount++;
            data.data[6]++;
        }
        return data;
    }
}