package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.drm.ProcessedData;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.logic.dao.ClockDao;
import com.example.myapplication.logic.model.ClockDate;
import com.example.myapplication.logic.model.Setting;
import com.example.myapplication.logic.utils.Tools;
import com.example.myapplication.ui.tab1.tab1;
import com.example.myapplication.ui.tab2.tab2;
import com.example.myapplication.ui.tab3.tab3;
import com.example.myapplication.ui.tab4.tab4;
import com.example.myapplication.ui.tab5.tab5;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import org.greenrobot.eventbus.EventBus;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar,menu);
        return true;
    }

    private void permissionsRequest() {

        PermissionX.init(this).permissions(
                //写入文件
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
                        scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback() {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                        scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白");
                    }
                })
                .setDialogTintColor(R.color.white, R.color.app_color)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            //通过后的业务逻辑
                        } else {

                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.mus1:
                Setting setting=new Setting(1);
                EventBus.getDefault().post(setting);
                break;
            case R.id.mus2:
                Setting setting2=new Setting(2);
                EventBus.getDefault().post(setting2);
                break;
            case R.id.mus3:
                Setting setting3=new Setting(3);
                EventBus.getDefault().post(setting3);
                break;
            case R.id.music:
                Setting setting4=new Setting(4);
                EventBus.getDefault().post(setting4);
                break;
            case R.id.white:
                Setting setting5=new Setting(5);
                EventBus.getDefault().post(setting5);
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI();
        permissionsRequest();
    }

    private void initUI() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawer);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.startTime0:
                        fragmentTransaction.replace(R.id.frame,new tab1());
                        fragmentTransaction.commit();
                        break;
                    case R.id.startTime1:
                        fragmentTransaction.replace(R.id.frame,new tab2());
                        fragmentTransaction.commit();
                        break;
                    case R.id.startTime2:
                        fragmentTransaction.replace(R.id.frame,new tab3());
                        fragmentTransaction.commit();
                        break;
                    case R.id.startTime3:
                        fragmentTransaction.replace(R.id.frame,new tab4());
                        fragmentTransaction.commit();
                        break;
                    case R.id.startTime4:
                        fragmentTransaction.replace(R.id.frame,new tab5());
                        fragmentTransaction.commit();
                    default:
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.startTime0);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.drawable.baseline_home_24);

        NavigationView navigationView=findViewById(R.id.navto);
        ClockDao clockDao = Tools.clockDao(this);
        List<ClockDate> all = clockDao.getAll();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navHH:
                        AnayThere(3);
                        break;
                    case R.id.navHH1:
                       AnayThere(7);
                       break;
                    case R.id.navHH2:
                        AnayThere(30);
                        break;
                    case R.id.About:
                        new AlertDialog.Builder(MainActivity.this).setMessage("毕业设计题目：基于安卓的睡眠管理系统").show();
                        break;
                    case R.id.signout:
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return true;
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void AnayThere(int i) {
        LocalDate now = LocalDate.now();
        LocalDate localDate1 = LocalDate.now().minusDays(i);
        ClockDao clockDao = Tools.clockDao(this);
        int t1=0;
        int t2=0;
        int t3=0;
        int total=0;
        for(LocalDate date=localDate1;date.isBefore(now);date=date.plusDays(1)){
            int year=date.getYear();
            int month=date.getMonthValue();
            int day=date.getDayOfMonth();
            ClockDate clockDate = clockDao.GetOne(year, month, day);
            if(clockDate!=null){
                Log.e("kkk",clockDate.toString());
                //total+=24*60-clockDate.getEvenhour()*60-clockDate.getEvenminte()+clockDate.getMornhour()*60+clockDate.getMornminte();
                total+=((24-clockDate.getEvenhour()+clockDate.getMornhour())*60+clockDate.getMornminte()-clockDate.getEvenminte());
                if(clockDate.getEvening()==50&&clockDate.getMorning()==50){
                    t1++;
                    continue;
                }
                if(clockDate.getEvening()==50){
                    t2++;
                    continue;
                }
                if(clockDate.getMorning()==50){
                    t3++;
                }
            }
        }
        String[] strings=new String[4];
        strings[0]="准时睡觉起床的天数:"+ t1;
        strings[1]="准时睡觉的天数:"+t2;
        strings[2]="准时起床的天数:"+t3;
        strings[3]="平均睡眠时间是:"+((float)total/60)/i+"小时";
        AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).setTitle("最近"+i+"天的分析:")
                .setIcon(R.drawable.shape_app_color_radius_5).setItems(strings,null).create();
        alertDialog.show();
    }

}
