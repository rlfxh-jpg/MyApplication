package com.example.myapplication.Adapter;


import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.logic.model.Song;

import java.util.List;

public class ListMusicAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {


    private List<Song> songList;

    private TextView musicname;
    private TextView songername;

    public ListMusicAdapter(int layoutResId, @Nullable List<Song> data) {
        super(R.layout.song_item, data);
        songList=data;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Song song) {
        initView(baseViewHolder);
        initData(baseViewHolder);
    }

    private void initView(BaseViewHolder baseViewHolder) {

         musicname=baseViewHolder.getView(R.id.item_song_name);
         songername=baseViewHolder.getView(R.id.item_song_singer);
    }

    private void initData(BaseViewHolder baseViewHolder) {
        musicname.setText(songList.get(baseViewHolder.getLayoutPosition()).getSong());
        songername.setText(songList.get(baseViewHolder.getLayoutPosition()).getSinger());
    }
}
