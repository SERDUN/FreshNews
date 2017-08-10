package com.example.user.freshnews;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.user.freshnews.adapter.NewsCursorRecyclerAdapter;
import com.example.user.freshnews.data.database.ContractClass;
import com.example.user.freshnews.data.database.NewsObserver;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "log_my";
    private RecyclerView recyclerView;
    private Button addNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        addNews = (Button) findViewById(R.id.btnAddNews);
        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random=new Random();
                ContentValues value = new ContentValues();
                value.put(ContractClass.News.COLUMN_NAME_AUTHOR,"flush");
                value.put(ContractClass.News.COLUMN_NAME_TITLE, "\"flush\" title");
                value.put(ContractClass.News.COLUMN_NAME_DESCRIPTION, "\"flush\""+random.nextInt());
                value.put(ContractClass.News.COLUMN_NAME_URL, "url");
                value.put(ContractClass.News.COLUMN_NAME_URL_TO_IMAGE, "url_img");
                value.put(ContractClass.News.COLUMN_NAME_PUBLISHED_AT, "date");


                ContentValues value1 = new ContentValues();
                value1.put(ContractClass.News.COLUMN_NAME_AUTHOR,"flush11");
                value1.put(ContractClass.News.COLUMN_NAME_TITLE, "\"flush\" title11");
                value1.put(ContractClass.News.COLUMN_NAME_DESCRIPTION, "\"f11lush\""+random.nextInt());
                value1.put(ContractClass.News.COLUMN_NAME_URL, "url");
                value1.put(ContractClass.News.COLUMN_NAME_URL_TO_IMAGE, "url_img");
                value1.put(ContractClass.News.COLUMN_NAME_PUBLISHED_AT, "date");

                getContentResolver().bulkInsert(ContractClass.News.CONTENT_URI,new ContentValues[]{value,value1});

            }
        });
        Cursor c = getContentResolver().query(
                ContractClass.News.CONTENT_URI_SERVER,
                ContractClass.News.DEFAULT_PROJECTION,
                null, null,
                null);

        NewsCursorRecyclerAdapter adapter = new NewsCursorRecyclerAdapter(c);
        recyclerView.setAdapter(adapter);
        NewsObserver ob = new NewsObserver(new Handler(), adapter, getContentResolver());
        getContentResolver().registerContentObserver(ContractClass.News.CONTENT_URI, true, ob);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                String str;
//                do {
//                    str = "";
//                    for (String cn : c.getColumnNames()) {
//                        str = str.concat(cn + " = "
//                                + c.getString(c.getColumnIndex(cn)) + "; ");
//                    }
//                    Log.d(LOG_TAG, str);
//
//                } while (c.moveToNext());
//            }
//            c.close();
//        } else
//            Log.d(LOG_TAG, "Cursor is null");

    }
}
