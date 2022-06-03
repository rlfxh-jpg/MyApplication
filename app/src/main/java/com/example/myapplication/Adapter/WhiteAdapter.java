package com.example.myapplication.Adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.logic.model.Song;
import com.example.myapplication.logic.model.WhiteNoise;

import java.util.List;

public class WhiteAdapter extends BaseQuickAdapter<WhiteNoise, BaseViewHolder> {

    private List<WhiteNoise> songList;

    private TextView musicname;

    public WhiteAdapter(int layoutResId, @Nullable List<WhiteNoise> data) {
        super(R.layout.whiteitem, data);
        songList=data;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, WhiteNoise song) {
        initView(baseViewHolder);
        initData(baseViewHolder);
    }

    private void initView(BaseViewHolder baseViewHolder) {

        musicname=baseViewHolder.getView(R.id.item_white_name);
    }

    private void initData(BaseViewHolder baseViewHolder) {
        musicname.setText(songList.get(baseViewHolder.getLayoutPosition()).getName());
    }
}
