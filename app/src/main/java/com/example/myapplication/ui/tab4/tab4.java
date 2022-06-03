package com.example.myapplication.ui.tab4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.myapplication.Adapter.ListMusicAdapter;
import com.example.myapplication.Adapter.WhiteAdapter;
import com.example.myapplication.MusicService;
import com.example.myapplication.R;

import com.example.myapplication.logic.dao.SongDao;
import com.example.myapplication.logic.model.Setting;
import com.example.myapplication.logic.model.Song;
import com.example.myapplication.logic.model.WhiteNoise;
import com.example.myapplication.logic.utils.MusicUtils;
import com.example.myapplication.logic.utils.Tools;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class tab4 extends Fragment {

    private static List<Song> songList;
    private static List<WhiteNoise> WhiteNoiseList;
    private MusicService.MusicBinder musicBinder;
    private static final String TAG = "MainActivity";
    private ListMusicAdapter adapter;
    private ImageView songImage;
    private TextView songName;
    private TextView songSinger;
    private ImageButton previous;
    private ImageButton play;
    private ImageButton next;
    private static final int UPDATE = 0;
    private Thread myThread;
    RecyclerView recyclerView;


    private int pos = 0;

    boolean isPlaying = false;

    boolean start = false;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        Intent intent = new Intent(getActivity(), MusicService.class);
        Activity activity = getActivity();
        activity.startService(intent);
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        songImage = activity.findViewById(R.id.song_image);
        songName = activity.findViewById(R.id.song_name);
        songSinger = activity.findViewById(R.id.song_singer);
        previous = activity.findViewById(R.id.music_previous);
        play = activity.findViewById(R.id.music_play);
        next = activity.findViewById(R.id.music_next);
        //button=activity.findViewById(R.id.buttonp);
        getSongDate();
        showList();
        initMediaPlayer();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicService.mediaPlayer.isPlaying()) {
                    MusicService.mediaPlayer.pause();
                    play.setImageResource(R.drawable.pause);
                } else {
                    MusicService.mediaPlayer.start();
                    play.setImageResource(R.drawable.play);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicBinder.previousMusic();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicBinder.nextMusic();
            }
        });


    }

    //实现活动与服务的绑定，让活动能够操作服务做某些事情
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MusicService.MusicBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void select(Setting setting) {
        int message = setting.getMessage();
        if (message == 1) {
            musicBinder.setSinglePlay();
            Toast.makeText(getActivity(), "设置成功！！！", Toast.LENGTH_SHORT).show();
        } else if (message == 2) {
            musicBinder.setSequencePlay();
            Toast.makeText(getActivity(), "设置成功！！！", Toast.LENGTH_SHORT).show();
        } else if (message == 3) {
            musicBinder.setRandomPlay();
            Toast.makeText(getActivity(), "设置成功！！！", Toast.LENGTH_SHORT).show();
        } else if (message == 4) {
            ListMusicAdapter adapter = new ListMusicAdapter(R.layout.song_item, songList);
            recyclerView.setAdapter(adapter);
            adapter.addChildClickViewIds(R.id.songcardview);
            adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                    //Toast.makeText(getActivity(), songList.get(position).getPath(), Toast.LENGTH_SHORT).show();
                    pos = position;
                    initMediaPlayer();
                    play.setImageResource(R.drawable.play);
                    musicBinder.setMediaPlayer(position);
                    isPlaying = true;
                }
            });
            Toast.makeText(getActivity(), "切换成功！！！", Toast.LENGTH_SHORT).show();
        } else if (message == 5) {
            WhiteAdapter whiteAdapter = new WhiteAdapter(R.id.song_id, WhiteNoiseList);
            recyclerView.setAdapter(whiteAdapter);
            whiteAdapter.addChildClickViewIds(R.id.Whitecardview);
            whiteAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                    try {
                        songName.setText("White Noise");
                        songSinger.setText("未知");
                        musicBinder.setMediaPlayerW(WhiteNoiseList.get(position).getId());
                        play.setImageResource(R.drawable.play);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            Toast.makeText(getActivity(), "切换成功！！！", Toast.LENGTH_SHORT).show();
        }
    }

    private void showList() {
        recyclerView = getActivity().findViewById(R.id.music_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ListMusicAdapter adapter = new ListMusicAdapter(R.layout.song_item, songList);
        recyclerView.setAdapter(adapter);
        adapter.addChildClickViewIds(R.id.songcardview);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                //Toast.makeText(getActivity(), songList.get(position).getPath(), Toast.LENGTH_SHORT).show();
                pos = position;
                initMediaPlayer();
                play.setImageResource(R.drawable.play);
                musicBinder.setMediaPlayer(position);
                isPlaying = true;
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                WhiteAdapter whiteAdapter=new WhiteAdapter(R.id.song_id,WhiteNoiseList);
//                recyclerView.setAdapter(whiteAdapter);
//            }
//        });

//        RecyclerView recyclerView1=getActivity().findViewById(R.id.music_layout1);
//        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        WhiteAdapter whiteAdapter=new WhiteAdapter(R.id.song_id,WhiteNoiseList);
//        recyclerView1.setAdapter(whiteAdapter);
//        whiteAdapter.addChildClickViewIds(R.id.Whitecardview);
//        whiteAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//                try {
//                    songName.setText("White Noise");
//                    songSinger.setText("未知");
//                    musicBinder.setMediaPlayerW(WhiteNoiseList.get(position).getId());
//                    play.setImageResource(R.drawable.play);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    //使用handler接收线程发出的消息，通知是否进行数据更新
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE:
                    //只要msg.arg1!=pos，就说明音乐信息发生改变，需要更新UI
                    if (msg.arg1 != pos) {
                        pos = msg.arg1;
                        initMediaPlayer();
                    }
            }
        }
    };

    //定义一个线程用于帮助handler实时更新UI
    private class MyThread implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (MusicService.mediaPlayer != null) {
                    Message message = new Message();
                    message.what = UPDATE;
                    //取得服务正在播放的音乐的position参数
                    message.arg1 = MusicService.getPos();
                    handler.sendMessage(message);
                    try {
                        //每过500毫秒就更新一次
                        Thread.sleep(500);
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }

    }


    private void getSongDate() {
        myThread = new Thread(new MyThread());
        myThread.start();
        songList = MusicUtils.getMusicData2(getActivity());
        WhiteNoiseList = new ArrayList<>();
        WhiteNoiseList.add(new WhiteNoise(R.raw.airplane, "airplane"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.all_pretty_horses, "horses"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.blender, "blender"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.calling, "calling"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.car, "car"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.chera, "chera"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.cleaner, "cleaner"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.danny_boy, "danny_boy"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.dryer, "dryer"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.forest, "forest"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.gonjeshk, "gonjeshk"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.goto_sleep, "goto_sleep"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.hasani, "hasani"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.heart, "heart"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.helicopter, "helicopter"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.lullaby, "lullaby"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.lung, "lung"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.music_box, "music_box"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.rain, "rain"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.sea, "sea"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.train, "train"));
        WhiteNoiseList.add(new WhiteNoise(R.raw.womb, "womb"));
    }


    private void initMediaPlayer() {
        if (songList != null) {
            songName.setText(songList.get(pos).getSong());
            songSinger.setText(songList.get(pos).getSinger());
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tab4() {
        // Required empty public constructor
    }

    public static tab4 newInstance(String param1, String param2) {
        tab4 fragment = new tab4();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab4, container, false);
    }
}