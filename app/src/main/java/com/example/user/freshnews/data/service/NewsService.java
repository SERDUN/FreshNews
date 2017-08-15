package com.example.user.freshnews.data.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;

import com.example.user.freshnews.data.model.CurrentNews;
import com.example.user.freshnews.data.model.News;
import com.example.user.freshnews.data.provider.ContractClass;
import com.example.user.freshnews.network.NewsConnectionFactory;
import com.example.user.freshnews.utils.Const;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Call<CurrentNews> c = NewsConnectionFactory.getService().getNews();
        c.enqueue(new Callback<CurrentNews>() {
            @Override
            public void onResponse(Call<CurrentNews> call, Response<CurrentNews> response) {
                ArrayList<ContentValues> buffer = new ArrayList<>();
                for (int i = response.body().getNewses().size() - 1; i > -1; i--)
                    buffer.add(createContentValues(response.body().getNewses().get(i)));
                getContentResolver().bulkInsert(ContractClass.News.CONTENT_URI, buffer.toArray(new ContentValues[buffer.size()]));
            }

            @Override
            public void onFailure(Call<CurrentNews> call, Throwable t) {
                Intent broadcastIntent = new Intent(Const.BroadcastConst.BROADCAST_ACTION);
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
