package com.example.user.freshnews.data.database;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.user.freshnews.data.model.CurrentNews;
import com.example.user.freshnews.data.model.News;
import com.example.user.freshnews.network.NewsConnectionFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("myLog", "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Call<CurrentNews> c = NewsConnectionFactory.getService().getNews();
        c.enqueue(new Callback<CurrentNews>() {
            @Override
            public void onResponse(Call<CurrentNews> call, Response<CurrentNews> response) {
                for (News n : response.body().getNewses()) {
                    getContentResolver().insert(ContractClass.News.CONTENT_URI,createContentValues(n));
                }
                Log.d("myLog", "response: " + response.body().getNewses().toString());
                stopSelf();

            }

            @Override
            public void onFailure(Call<CurrentNews> call, Throwable t) {

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
}
