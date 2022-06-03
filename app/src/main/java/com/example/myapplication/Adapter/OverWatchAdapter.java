package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.util.Executors;
import com.example.myapplication.BooksList;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.logic.model.OverWatch;

import java.util.List;

public class OverWatchAdapter extends RecyclerView.Adapter<OverWatchAdapter.ViewHolder> {
    private Context mContext;
    private List<OverWatch> mOverWatch;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(mContext==null){
           mContext=parent.getContext();
       }
       View view= LayoutInflater.from(mContext).inflate(R.layout.tab5listitem,parent,false);
        return new ViewHolder(view);
    }

    public OverWatchAdapter(List<OverWatch> OverWatchList){
                 mOverWatch = OverWatchList;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OverWatch overWatch=mOverWatch.get(position);
        holder.textView.setText(overWatch.getName());
        //Glide.with(mContext).load(overWatch.getImageID()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, BooksList.class);
                intent.putExtra("uri",overWatch.getUri());
                intent.putExtra("name",overWatch.getName());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mOverWatch.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView= itemView.findViewById(R.id.CardView);
            imageView=itemView.findViewById(R.id.overwatch_image);
            textView=itemView.findViewById(R.id.overwatch_name);
        }
    }


}
