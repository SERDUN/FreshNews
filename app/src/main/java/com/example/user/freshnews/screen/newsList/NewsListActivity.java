package com.example.user.freshnews.screen.newsList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.freshnews.R;
import com.example.user.freshnews.screen.newsList.adapter.NewsCursorRecyclerAdapter;
import com.example.user.freshnews.screen.newsList.listener.NewsObserver;
import com.example.user.freshnews.utils.Const;


public class NewsListActivity extends AppCompatActivity implements NewsListContract.View, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private NewsListContract.Presenter presenter;
    private NewsObserver newsObserver;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsCursorRecyclerAdapter adapter;

    final String LOG_TAG = "myLogs";

    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new NewsListPresenter(this, getContentResolver());
        initBroadcastService();
        initView();
    }

    private void initBroadcastService() {
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                //обработчик ответа из service
                presenter.handleStatus(intent.getIntExtra(Const.BroadcastConst.STATUS, 0));
            }
        };
        IntentFilter intFilt = new IntentFilter(Const.BroadcastConst.BROADCAST_ACTION);
        registerReceiver(br, intFilt);

    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsCursorRecyclerAdapter(presenter.getCachedNews());
        recyclerView.setAdapter(adapter);
//        newsObserver = new NewsObserver(new Handler(), () ->
//                mSwipeRefreshLayout.setRefreshing(false), adapter, getContentResolver());


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


}

