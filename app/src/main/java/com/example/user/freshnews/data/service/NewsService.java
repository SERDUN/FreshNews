package com.example.user.freshnews.data.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.user.freshnews.data.model.CurrentNews;
import com.example.user.freshnews.data.model.News;
import com.example.user.freshnews.data.provider.ContractClass;
import com.example.user.freshnews.network.NewsConnectionFactory;
import com.example.user.freshnews.utils.Const;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsService extends Service {
    private Intent broadcastIntent;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("myLog", "onCreate: ");
    }

    final String LOG_TAG = "myLogs";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        broadcastIntent = new Intent(Const.BroadcastConst.BROADCAST_ACTION);

        Call<CurrentNews> c = NewsConnectionFactory.getService().getNews();
        c.enqueue(new Callback<CurrentNews>() {
            @Override
            public void onResponse(Call<CurrentNews> call, Response<CurrentNews> response) {
                for (News n : response.body().getNewses()) {
                    getContentResolver().insert(ContractClass.News.CONTENT_URI, createContentValues(n));
                }
                broadcastIntent.putExtra(Const.BroadcastConst.STATUS, Const.BroadcastConst.STATUS_OK);
                sendBroadcast(broadcastIntent);
                stopSelf();

            }

            @Override
            public void onFailure(Call<CurrentNews> call, Throwable t) {
                broadcastIntent.putExtra(Const.BroadcastConst.STATUS, Const.BroadcastConst.STATUS_FAILURE);
                sendBroadcast(broadcastIntent);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    public ContentValues createContentValues(News news) {
        ContentValues value = new ContentValues();
        value.put(ContractClass.News.COLUMN_NAME_AUTHOR, (String) news.getAuthor());
        value.put(ContractClass.News.COLUMN_NAME_TITLE, news.getTitle());
        value.put(ContractClass.News.COLUMN_NAME_DESCRIPTION, news.getDescription());
        value.put(ContractClass.News.COLUMN_NAME_URL, news.getUrl());
        value.put(ContractClass.News.COLUMN_NAME_URL_TO_IMAGE, news.getUrlToImage());
        value.put(ContractClass.News.COLUMN_NAME_PUBLISHED_AT, news.getPublishedAt());
        return value;


    }

    public NewsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
