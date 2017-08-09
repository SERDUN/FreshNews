package com.example.user.freshnews.network;

import com.example.user.freshnews.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by User on 08.08.2017.
 */

public class NewsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY)
                .addQueryParameter("format", "json").build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
