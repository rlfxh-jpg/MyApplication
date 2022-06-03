package com.example.myapplication.ui.tab1;

import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.example.myapplication.Adapter.ListAdapter;
import com.example.myapplication.AlarmManagerUtil;
import com.example.myapplication.AlarmReSettingsActivity;
import com.example.myapplication.AlarmSettingsActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.logic.dao.AlarmDao;
import com.example.myapplication.logic.database.AppDatabase;
import com.example.myapplication.logic.model.Alarm;
import com.example.myapplication.logic.model.AlarmModel;
import com.example.myapplication.logic.utils.Tools;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class tab1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parame ters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tab1() {
        // Required empty public constructor
    }

    public static tab1 newInstance(String param1, String param2) {
        tab1 fragment = new tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    ListAdapter adapter;
    List<Alarm> list = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Alarm tmp=new Alarm("08:00","Good Morning!",8,0,0,0);
        list.add(tmp);

        Button button=getActivity().findViewById(R.id.buttonSetTime);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AlarmSettingsActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = getActivity().findViewById(R.id.recycler_view_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        showList();
    }

    Switch alarmSwitch;
    AlarmDao alarmDao;

    public void showList(){
        alarmDao = Tools.GetAlarmDao(getActivity());
        List<AlarmModel> all = alarmDao.getAll();
        for (AlarmModel alarmModel : all) {
            list.add(Tools.ModelToAlarm(alarmModel));
        }
        adapter = new ListAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.addChildClickViewIds(R.id.alarm_cardview);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if(view.getId()==R.id.alarm_cardview){
                    alarmSwitch = (Switch) adapter.getViewByPosition(position, R.id.alarm_switch);
                    Alarm curAlarm = (Alarm) adapter.getItem(position);
                    Toast.makeText(getActivity(), ""+curAlarm.getId(), Toast.LENGTH_SHORT).show();
                    String formatTime = curAlarm.getHour() + "：" + curAlarm.getMinute();
                    boolean isexites=isexites();
                    if(!alarmSwitch.isChecked()){
                        if(isexites){
                            Toast.makeText(getActivity(), "请勿设置多个！！！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        alarmSwitch.setChecked(true);
                        Toast.makeText(getActivity(), "Alarm at " + formatTime + " is On!", Toast.LENGTH_SHORT).show();
                        curAlarm.setIsActivated(Alarm.ACTIVATED);
                        alarmDao.Updata(Tools.AlarmToModelplus(curAlarm));
                        if(curAlarm.getDateCount()==Alarm.ONLY_ONCE){
                            AlarmManagerUtil.setAlarm(getActivity(), 0, curAlarm.getHour(), curAlarm.getMinute(), (int) curAlarm.getId(), 0, curAlarm.getContent(), 2);
                        }else if(curAlarm.getDateCount()==Alarm.EVERYDAY){
                            AlarmManagerUtil.setAlarm(getActivity(), 1, curAlarm.getHour(), curAlarm.getMinute(), (int) curAlarm.getId(), 0, curAlarm.getContent(), 2);
                        }else {
                            int[] tmp = curAlarm.getWeek();
                            for (int i = 0; i < 7; i++) {
                                if (tmp[i] == 1)
                                    AlarmManagerUtil.setAlarm(getActivity(), 2, curAlarm.getHour(), curAlarm.getMinute(), (int) curAlarm.getId() + 100 + i, i + 1, curAlarm.getContent(), 2);
                            }
                        }
                    }else {
                        alarmSwitch.setChecked(false);
                        Toast.makeText(getActivity(), "Alarm at " + formatTime + " is Off!", Toast.LENGTH_SHORT).show();
                        curAlarm.setIsActivated(Alarm.DEACTIVATED);
                        alarmDao.Updata(Tools.AlarmToModelplus(curAlarm));
                        if(curAlarm.getDateCount()>0){
                            int[] tmp = curAlarm.getWeek();
                            for (int i = 0; i < 7; i++) {
                                if (tmp[i] == 1)
                                    AlarmManagerUtil.cancelAlarm(getActivity(), (int) curAlarm.getId() + 100 + i);
                            }
                        }else {
                            AlarmManagerUtil.cancelAlarm(getActivity(), (int) curAlarm.getId());
                        }
                    }
                }
            }
        });

//        ================================================================================
        adapter.addChildLongClickViewIds(R.id.alarm_cardview);
        adapter.setOnItemChildLongClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.alarm_cardview) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                assert vibrator != null;
                vibrator.vibrate(50);
                new AlertDialog.Builder(getActivity())
                        .setMessage("What do you want to do?")
                        .setNegativeButton("Delete", (dialog, which) -> {
                            Alarm curAlarm = (Alarm) adapter.getItem(position);
                            alarmDao.Delete(Tools.AlarmToModelplus(curAlarm));
                            refreshRecyclerView();
                        })
                        .setPositiveButton("Edit", (dialog, which) -> {
                            Alarm curAlarm = (Alarm) adapter.getItem(position);
                            Intent intent = new Intent(getActivity(), AlarmReSettingsActivity.class);
                            intent.putExtra("id", curAlarm.getId());
                            startActivity(intent);
                        })
                        .setNeutralButton("Cancel", (dialogInterface, i) -> {
                        })
                        .create().show();
            }
            return false;
        });
    }

    private boolean isexites() {
        List<AlarmModel> all = alarmDao.getAll();
        for (AlarmModel alarmModel : all) {
            if(alarmModel.get_isActivated()==Alarm.ACTIVATED)return true;
        }
        return false;
    }

    //刷新list列表
    public void refreshRecyclerView(){
        if(list.size()>0) list.clear();
        AlarmDao alarmDao = Tools.GetAlarmDao(getActivity());
        List<AlarmModel> all = alarmDao.getAll();
        for (AlarmModel alarmModel : all) {
            list.add(Tools.ModelToAlarm(alarmModel));
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }
}