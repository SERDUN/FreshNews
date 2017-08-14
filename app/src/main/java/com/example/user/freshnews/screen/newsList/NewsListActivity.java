package com.example.user.freshnews.screen.newsList;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.user.freshnews.R;
import com.example.user.freshnews.data.provider.ContractClass;
import com.example.user.freshnews.screen.newsList.adapter.NewsCursorRecyclerAdapter;
import com.example.user.freshnews.screen.newsList.listener.NewsObserver;
import com.example.user.freshnews.utils.Const;

import java.util.Random;


public class NewsListActivity extends AppCompatActivity implements NewsListContract.View, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private NewsListContract.Presenter presenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsCursorRecyclerAdapter adapter;
    private boolean localData = false;

    final String LOG_TAG = "myLogs";

    BroadcastReceiver br;

    private Button addNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            localData = savedInstanceState.getBoolean(Const.IS_ROTATE);
        }
        presenter = new NewsListPresenter(this, getContentResolver(), localData);
        initObservers();
        initView();


        addNews = (Button) findViewById(R.id.btnAddNews);
        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();


                ContentValues value1 = new ContentValues();
                value1.put(ContractClass.News.COLUMN_NAME_AUTHOR, "flush11");
                value1.put(ContractClass.News.COLUMN_NAME_TITLE, "\"flush\" title11");
                value1.put(ContractClass.News.COLUMN_NAME_DESCRIPTION, "\"gdrgrd\"" + random.nextInt());
                value1.put(ContractClass.News.COLUMN_NAME_URL, "url");
                value1.put(ContractClass.News.COLUMN_NAME_URL_TO_IMAGE, "url_img");
                value1.put(ContractClass.News.COLUMN_NAME_PUBLISHED_AT, "2017-08-14T18:37:00Z");

                getContentResolver().insert(ContractClass.News.CONTENT_URI, value1);

            }
        });
    }

    private void initObservers() {
        // для обратной связи при загрузки или обновлении данных
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                //обработчик ответа из service
                presenter.handleStatus(intent.getIntExtra(Const.BroadcastConst.STATUS, 0));
            }
        };
        IntentFilter intFilt = new IntentFilter(Const.BroadcastConst.BROADCAST_ACTION);
        registerReceiver(br, intFilt);

        //для проверки количества записей в базе при каждом ее изменении
        NewsObserver ob = new NewsObserver(new Handler(), getContentResolver());
        getContentResolver().registerContentObserver(ContractClass.News.CONTENT_URI, true, ob);

    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsCursorRecyclerAdapter(presenter.getCachedNews(), this);
        recyclerView.setAdapter(adapter);


    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Const.IS_ROTATE, true);
    }

    @Override
    public void showLoadingView() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoadingView() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void updateListView(Cursor news) {
        adapter.swapCursor(news);

    }

    @Override
    public void onRefresh() {
        presenter.showNews(presenter.getCachedNews());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
        adapter.getCursor().close();
    }
}

