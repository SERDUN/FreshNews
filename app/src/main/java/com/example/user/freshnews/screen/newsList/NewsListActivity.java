package com.example.user.freshnews.screen.newsList;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.user.freshnews.R;
import com.example.user.freshnews.data.provider.ContractClass;
import com.example.user.freshnews.screen.newsList.adapter.NewsCursorRecyclerAdapter;
import com.example.user.freshnews.screen.newsList.listener.NewsObserver;
import com.example.user.freshnews.utils.Const;



public class NewsListActivity extends AppCompatActivity implements NewsListContract.View, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private NewsListContract.Presenter presenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsCursorRecyclerAdapter adapter;
    private boolean localData = false;



    BroadcastReceiver br;


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
        adapter = new NewsCursorRecyclerAdapter(presenter.getCachedNews(), this, url -> {
            try {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(myIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No application can handle this request."
                        + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
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
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();    }

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

