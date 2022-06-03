package com.example.myapplication.ui.tab3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.logic.dao.AlarmDao;
import com.example.myapplication.logic.dao.ClockDao;
import com.example.myapplication.logic.dao.WakeupDao;
import com.example.myapplication.logic.model.Alarm;
import com.example.myapplication.logic.model.AlarmModel;
import com.example.myapplication.logic.model.ClockDate;
import com.example.myapplication.logic.utils.Tools;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab3 extends Fragment implements CalendarView.OnCalendarSelectListener,CalendarView.OnYearChangeListener,View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static tab3 newInstance(String param1, String param2) {
        tab3 fragment = new tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;

    CalendarLayout mCalendarLayout;


    ClockDao clockDao;
    int mYear;
    int year;
    int month;
    int day;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCalendarView = getActivity().findViewById(R.id.calendarView);
        mTextMonthDay = getActivity().findViewById(R.id.tv_month_day);
        mTextYear = getActivity().findViewById(R.id.tv_year);
        mTextLunar = getActivity().findViewById(R.id.tv_lunar);
        mRelativeTool = getActivity().findViewById(R.id.rl_tool);
        mTextCurrentDay = getActivity().findViewById(R.id.tv_current_day);
        mCalendarLayout = getActivity().findViewById(R.id.calendarLayout);

        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        getActivity().findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = getActivity().findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));

        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        Button MoringClock=getActivity().findViewById(R.id.Button1);
        boolean moringenable=evenable();
        if(!moringenable){
            MoringClock.setClickable(false);
            MoringClock.setEnabled(false);
        }else {
            MoringClock.setClickable(true);
            MoringClock.setEnabled(true);
        }

        Button eveningClock=getActivity().findViewById(R.id.Button2);
        boolean eveningenable=moringenable();
        if(!eveningenable){
            eveningClock.setClickable(false);
            eveningClock.setEnabled(false);
        }else {
            eveningClock.setClickable(true);
            eveningClock.setEnabled(true);
        }

        year = mCalendarView.getCurYear();
        month= mCalendarView.getCurMonth();
        day= mCalendarView.getCurDay();
        clockDao = Tools.clockDao(getActivity());
        initDate();
        MoringClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date=new Date();
                ClockDate clockDate=new ClockDate();
                clockDate.setYear(year);
                clockDate.setMonth(month);
                clockDate.setDay(day);
                clockDate.setMorning(50);

                ClockDate clockDate1 = clockDao.GetOne(year, month, day);
                if(clockDate1==null){
                    clockDate.setMornhour(date.getHours());
                    clockDate.setMornminte(date.getMinutes());
                    clockDao.insertClock(clockDate);
                }else {
                    if(clockDate1.getMorning()==50)return;
                    clockDate1.setMorning(50);
                    clockDate1.setMornhour(date.getHours());
                    clockDate1.setMornminte(date.getMinutes());
                    clockDao.Update(clockDate1);
                }
                initDate();
            }
        });

        eveningClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClockDate clockDate=new ClockDate();
                Date date=new Date();
                clockDate.setYear(year);
                clockDate.setMonth(month);
                clockDate.setDay(day);
                clockDate.setEvening(50);
                ClockDate clockDate1 = clockDao.GetOne(year, month, day);
                if(clockDate1==null){
                    clockDate.setEvenhour(date.getHours());
                    clockDate.setEvenminte(date.getMinutes());
                    clockDao.insertClock(clockDate);
                }else {
                    if(clockDate1.getEvening()==50)return;
                    clockDate1.setEvening(50);
                    clockDate1.setEvenhour(date.getHours());
                    clockDate1.setEvenminte(date.getMinutes());
                    clockDao.Update(clockDate1);
                }
                initDate();
            }
        });
    }

    private boolean evenable() {
        AlarmDao wakeupDao = Tools.GetAlarmDao(getActivity());//ToTools.GetAlarmDao(getActivity())ols.wakeupDao(getActivity());
        List<AlarmModel> all = wakeupDao.getAll();
        int hour=-1;
        int minte=-1;
        for (AlarmModel alarmModel : all) {
            if(alarmModel.get_isActivated()== Alarm.ACTIVATED){
                hour=alarmModel.get_hour();
                minte=alarmModel.get_minute();
                break;
            }
        }
        if(hour==-1&&minte==-1)return false;
        Date date=new Date();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        int total=hours*60+minutes;
        int totals=hour*60+minte;
        if(Math.abs(totals-total)<=30)return true;
        return false;
    }

    private boolean moringenable() {
        WakeupDao alarmDao = Tools.wakeupDao(getActivity());
        List<AlarmModel> all = alarmDao.getAll();
        int hour=-1;
        int minte=-1;
        for (AlarmModel alarmModel : all) {
            if(alarmModel.get_isActivated()== Alarm.ACTIVATED){
                hour=alarmModel.get_hour();
                minte=alarmModel.get_minute();
                break;
            }
        }
        if(hour==-1&&minte==-1)return false;
        Date date=new Date();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        int total=hours*60+minutes;
        int totals=hour*60+minte;
        if(Math.abs(totals-total)<=30)return true;
        return false;
    }

    private void initDate() {
        Map<String, Calendar> map = new HashMap<>();
        List<ClockDate> all = clockDao.getAll();
        for (ClockDate clockDate : all) {
            int years=clockDate.getYear();
            int months=clockDate.getMonth();
            int days=clockDate.getDay();
            long sum=clockDate.getMorning()+clockDate.getEvening();
            map.put(getSchemeCalendar(years, months, days, 0xFF40db25, ""+sum).toString(),
                    getSchemeCalendar(years, months, days, 0xFF40db25, ""+sum));
        }

        mCalendarView.setSchemeDate(map);

    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab3, container, false);
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onClick(View view) {

    }
}