package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Adapter.CustomAdapterListView;
import com.example.myapplication.logic.model.BookListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BooksList extends AppCompatActivity {


    private ListView listView;
    private TextView totalPageNumbers;
    private String NextPage;
    private String PreviousPage;
    private int currentPageNumber = 1;
    private int totalPages = 0;
    private ImageButton imageButtonleft;
    private ImageButton imageButtonright;

    private CustomAdapterListView adapter;

    ExecutorService executorService= Executors.newFixedThreadPool(1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String uri = (String) extras.get("uri");
        String name=extras.getString("name");
        TextView textView=findViewById(R.id.textview);
        textView.setText("**"+name+"**");
        listView=findViewById(R.id.listView);
        totalPageNumbers=findViewById(R.id.pageView);
        imageButtonleft=findViewById(R.id.imageButtonLeft);
        imageButtonright=findViewById(R.id.imageButtonRight);
        showList(uri);
        imageButtonleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPageNumber>1){
                    currentPageNumber=currentPageNumber-1;
                    showList(PreviousPage);
                }
            }
        });
        imageButtonright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPageNumber<totalPages){
                    currentPageNumber=currentPageNumber+1;
                    showList(NextPage);
                }
            }
        });


    }

    private void showList(String uri) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String site=uri;
                List<BookListView> books = new ArrayList<>();
                ArrayList<String> pageNumber = new ArrayList<String>();
                Set<String> bookUrl = new LinkedHashSet<>();
                Document doc = null;

                Elements mLines;
                Elements mLines2;
                Elements mLines3;
                Elements mLines4;
                Elements mLines5;

                try {
                    //connect to the site
                    doc = Jsoup.connect(site).get();
                } catch (IOException | RuntimeException e) {
                    e.printStackTrace();
                }
                Log.e("jj",doc.html());
                if(doc!=null){
                    mLines = doc.getElementsByClass("layout");
                    for (Element mLine : mLines) {
                        String mPic = mLine.attr("src");
                        String mName = mLine.attr("alt");
                        books.add(new BookListView(mName,"http://www.loyalbooks.com" + mPic
                                , null));
                    }
                    mLines2 = doc.getElementsByClass("result-pages");
                    for (Element mLine2 : mLines2) {
                        String mPages = mLine2.text();
                        pageNumber.add(mPages);
                    }
                    mLines3 = doc.select("[rel=next]");
                    for (Element mLine3 : mLines3) {
                        NextPage = mLine3.attr("href");
                    }
                    mLines4 = doc.select("[rel=prev]");
                    for (Element mLine4 : mLines4) {
                        PreviousPage = mLine4.attr("href");
                    }
                    mLines5 = doc.select("a[href^=/book/]");
                    for (Element mLine5 : mLines5) {
                        bookUrl.add("http://www.loyalbooks.com"+ mLine5.attr("href"));
                    }
                }
                Handler handler=new Handler(Looper.getMainLooper());
                final ArrayList<String> tmp=new ArrayList<>();
                tmp.addAll(bookUrl);
                for (int i = 0; i < books.size(); i++) {
                    books.get(i).setLink(tmp.get(i));
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(listView.getAdapter()!=null){
                            adapter.clear();
                            adapter.addAll(books);
                        }else {
                            adapter = new CustomAdapterListView(BooksList.this,
                                    R.layout.books_list_item, books);
                            listView.setAdapter(adapter);
                        }
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    if(adapter.getImgLink(i)!=null){
                                        Intent intent=new Intent(BooksList.this,BookActivity.class);
                                        intent.putExtra("link",adapter.getLink(i));
                                        intent.putExtra("imgLink",adapter.getImgLink(i));
                                        intent.putExtra("title",adapter.getTitle(i));
                                        startActivity(intent);
                                    }
                            }
                        });
                        totalPages = Integer.parseInt(pageNumber.get(0).replaceAll("[^\\.0123456789]"
                                ,"").substring(1));
                        totalPageNumbers.setText(currentPageNumber + " of " + totalPages);

                    }
                });
            }
        });

    }
}