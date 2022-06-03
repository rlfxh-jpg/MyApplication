package com.example.myapplication;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.myapplication.logic.dao.AlarmDao;
import com.example.myapplication.logic.dao.WakeupDao;
import com.example.myapplication.logic.database.AppDatabase;
import com.example.myapplication.logic.model.Alarm;
import com.example.myapplication.logic.model.Data;
import com.example.myapplication.logic.utils.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.togglebuttongroup.button.ToggleButton;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;


public class AlarmSettingsActivity extends AppCompatActivity {

    private int hour;
    private int minute;

    ToggleButton mon;
    ToggleButton tue;
    ToggleButton wed;
    ToggleButton thu;
    ToggleButton fri;
    ToggleButton sat;
    ToggleButton sun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);
        sun = findViewById(R.id.sun);

        TimePicker timePicker = findViewById(R.id.time_picker);
        timePicker.setOnTimeChangedListener((timePicker1, i, i1) -> {
            hour = i;
            minute = i1;
        });
        int page=getIntent().getIntExtra("page",0);
        Switch repeat = findViewById(R.id.repeat);
        ExpandableLayout repeatEx = findViewById(R.id.repeat_ex);
        repeat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                repeatEx.expand();
            } else {
                repeatEx.collapse();
            }
        });

        RadioButton everyday = findViewById(R.id.everyday);
        RadioButton weekday = findViewById(R.id.weekday);
        ExpandableLayout weekEx = findViewById(R.id.week_ex);
        everyday.setOnCheckedChangeListener((compoundButton, b) -> {

        });
        weekday.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                weekEx.expand();
            } else {
                weekEx.collapse();
            }
        });

        EditText editText = findViewById(R.id.edit_view);

        FloatingActionButton fab = findViewById(R.id.confirm_fab);
        fab.setOnClickListener(view -> {
            AppDatabase appDatabase= Room.databaseBuilder(getApplication(),AppDatabase.class, "Alarm_database").allowMainThreadQueries().build();
            AlarmDao alarmDao = appDatabase.alarmDao();
            WakeupDao wakeupDao=Tools.wakeupDao(this);

            if (repeat.isChecked()) {
                if (everyday.isChecked()) {
                    Alarm alarm = new Alarm(makeTitle(hour, minute), editText.getText().toString(), hour, minute, Alarm.EVERYDAY, Alarm.DEACTIVATED);
                    if(page==0){
                        alarmDao.insertAll(Tools.AlarmToModel(alarm));
                    }
                    if(page==1){
                        wakeupDao.insertAll(Tools.AlarmToModel(alarm));
                    }
                } else if (weekday.isChecked()) {

                    Data data;

                    data=checkWeekStats();

                    if (data.dataCount == 0) {
                        Alarm alarm = new Alarm(makeTitle(hour, minute), editText.getText().toString(), hour, minute, Alarm.ONLY_ONCE, Alarm.DEACTIVATED);
                        if(page==0){
                            alarmDao.insertAll(Tools.AlarmToModel(alarm));
                        }else {
                            wakeupDao.insertAll(Tools.AlarmToModel(alarm));
                        }

                    } else {
                        Alarm alarm = new Alarm(makeTitle(hour, minute), editText.getText().toString(), hour, minute, data.dataCount, data.data, Alarm.DEACTIVATED);
                        if(page==0){
                            alarmDao.insertAll(Tools.AlarmToModel(alarm));
                        }else {
                            wakeupDao.insertAll(Tools.AlarmToModel(alarm));
                        }

                    }
                }
            } else {
                Alarm alarm = new Alarm(makeTitle(hour, minute), editText.getText().toString(), hour, minute, Alarm.ONLY_ONCE, Alarm.DEACTIVATED);
                if(page==0){
                    alarmDao.insertAll(Tools.AlarmToModel(alarm));
                }else {
                    wakeupDao.insertAll(Tools.AlarmToModel(alarm));
                }
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

        data.dataCount=0;

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