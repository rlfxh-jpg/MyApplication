package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.logic.model.BookListView;
import com.squareup.picasso.Picasso;

import java.util.List;

;

/**
 * custom adapter class used to fill
 * the listview of the SearchBooksActivity
 */
public class CustomAdapterListView extends ArrayAdapter<BookListView> {
    private Context mContext;
    private List<BookListView> books;
    private int resource;

    /**
     * constructor of the class
     * @param context
     * @param resource
     * @param objects
     */
    public CustomAdapterListView(Context context, int resource, List<BookListView> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.books = objects;

    }

    /**
     * various methods used to get specified fields
     * and atributes of pojo class
     * @param position
     * @return
     */
    public String getLink(int position) { return books.get(position).getLink(); }

    public String getImgLink(int position) { return books.get(position).getImageUrl(); }

    public String getTitle(int position) { return books.get(position).getTitle(); }

    public int getCount() {return books.size();}

    public BookListView getItem(int arg0) { return books.get(arg0); }
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
            holder.title = convertView.findViewById(R.id.title);
            holder.i1 = convertView.findViewById(R.id.imageViewLstRow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BookListView item = getItem(position);

        Picasso.get().load(item.getImageUrl()).resize(80, 80).centerCrop().into(holder.i1);
        holder.title.setText(item.getTitle());

        return convertView;
    }

    /**
     * pojo class to hold views
     */
    class ViewHolder {
        private TextView title;
        private ImageView i1;
    }
}
