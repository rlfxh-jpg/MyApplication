package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Adapter.CustomAdaterChapters;
import com.example.myapplication.logic.model.SingleBook;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookActivity extends AppCompatActivity {

    private ImageView bookCover;
    private TextView bookDescription;
    private TextView nameAndAuthor;
    private ListView listChapters;

    private String link;
    private String imgLink;
    private String title;

    private CustomAdaterChapters adapter;

    PlayService.MsBinder binder;

    ImageButton imageButton;

    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder=(PlayService.MsBinder)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        bookCover=findViewById(R.id.bookCover);
        bookDescription=findViewById(R.id.bookDescription);
        nameAndAuthor=findViewById(R.id.nameAndAuthor);
        listChapters=findViewById(R.id.listChapters);
        bookDescription.setMovementMethod(new ScrollingMovementMethod());

        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        link = extras.getString("link");
        imgLink=extras.getString("imgLink");
        title=extras.getString("title");
        Intent intent1=new Intent(BookActivity.this,PlayService.class);
        bindService(intent1,conn,BIND_AUTO_CREATE);
        showlist(link,imgLink,title);


        imageButton=findViewById(R.id.stopButton);
        imageButton.setImageResource(R.drawable.pause);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = binder.ChangeStatus();
                if(i==1){
                    imageButton.setImageResource(R.drawable.pause);
                }else if(i==2){
                    imageButton.setImageResource(R.drawable.play);
                }
            }
        });


    }

    private void showlist(String link, String imgLink, String title) {
        ExecutorService executorService= Executors.newFixedThreadPool(1);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                List<String> chapters = new ArrayList<>();
                List<SingleBook> books = new ArrayList<>();
                String description="";
                Document doc = null;
                Elements mLines;

                try {
                    doc = Jsoup.connect(link).get();

                } catch (IOException | RuntimeException e) {
                    e.printStackTrace();
                }

                if(doc!=null){
                    mLines = doc.getElementsByClass("book-description");
                    for (Element mLine : mLines) {
                        description = mLine.text();
                    }
                    String arr = "";
                    String html = doc.body().html();
                    if (html.contains("var audioPlaylist = new Playlist(\"1\", ["))
                        arr = html.split("var audioPlaylist = new Playlist\\(\"1\", \\[")[1];
                    if (arr.contains("]"))
                        arr = arr.split("\\]")[0];
                    //-----------------------------------------
                    if (arr.contains("},")) {
                        for (String mLine2 : arr.split("\\},")) {
                            if (mLine2.contains("mp3:\""))
                                chapters.add(mLine2.split("mp3:\"")[1].split("\"")[0]);
                        }
                    } else if (arr.contains("mp3:\""))
                        chapters.add(arr.split("mp3:\"")[1].split("\"")[0]);
                }

                Handler handler=new Handler(Looper.getMainLooper());
                String finalDescription = description;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.get().load(imgLink).into(bookCover);
                        nameAndAuthor.setText(title);
                        Log.e("jjj",title);
                        bookDescription.setText(finalDescription);
                        for(int i=0;i<chapters.size();i++){
                            books.add(new SingleBook(chapters.get(i)));
                        }
                        if(listChapters.getAdapter()!=null){
                            adapter.clear();
                            adapter.addAll(books);
                        }else {
                            adapter=new CustomAdaterChapters(getApplication(),R.layout.book_chapters_list_item,books);
                            listChapters.setAdapter(adapter);
                        }

                        listChapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String link=adapter.getChapters(i);
                                imageButton.setImageResource(R.drawable.play);
                                binder.PlayAudio(link);
                            }
                        });
                    }
                });
            }
        });
    }
}