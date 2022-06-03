package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.logic.model.SingleBook;

import java.util.List;

/**
 * custom adapter class used to fill
 * the listview of the SearchBooksActivity
 */
public class CustomAdaterChapters extends ArrayAdapter<SingleBook> {
    private Context mContext;
    private List<SingleBook> chapters;
    private int resource;

    /**
     * constructor of the class
     * @param context
     * @param resource
     * @param objects
     */
    public CustomAdaterChapters(Context context, int resource, List<SingleBook> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.chapters = objects;
    }

    /**
     * various methods used to get specified fields
     * and atributes of pojo class
     * @param position
     * @return
     */
    public String getChapters (int position) {return chapters.get(position).getChapters();}

    public int getCount() {return chapters.size();}

    public SingleBook getItem(int arg0) { return chapters.get(arg0); }

    public long getItemId(int position) {return position;}

    /**
     * this method creates viewholder and defines the separate
     * view items of the listview and loads resources to
     * them
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.titleChapter = convertView.findViewById(R.id.titleChapter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int page = position + 1;
        holder.titleChapter.setText("Chapter " + page);
        return convertView;
    }

    /**
     * pojo class to hold views
     */
    class ViewHolder {
        private TextView titleChapter;
    }


}
