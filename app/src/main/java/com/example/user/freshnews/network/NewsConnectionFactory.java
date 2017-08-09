package com.example.user.freshnews.network;

import com.example.user.freshnews.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 08.08.2017.
 */

public class NewsConnectionFactory {
    private static NewsApiService service;
    private static OkHttpClient okHttpClient;


    public static NewsApiService getService() {
        NewsApiService currentService = service;
        if (currentService == null) {
            synchronized (NewsApiService.class) {
                if (currentService == null) {
                    currentService = service = getRetrofitBuilder().create(NewsApiService.class);
                }
            }
        }
        return currentService;
    }


    private static Retrofit getRetrofitBuilder() {
        return new Retrofit.Builder().client(getOkHttpClient())
                .baseUrl(BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient client = okHttpClient;
        if (client == null) {
            synchronized (NewsConnectionFactory.class) {
                client = okHttpClient;
                if (client == null) {
                    client = okHttpClient = buildOkHttpClient();
                }
            }
        }
        return client;
    }

    private static OkHttpClient buildOkHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(new NewsInterceptor()).build();
    }
}