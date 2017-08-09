package com.example.user.freshnews.network;

import com.example.user.freshnews.data.model.CurrentNews;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by User on 08.08.2017.
 */

public interface NewsApiService {
    @GET("v1/articles?source=the-times-of-india&sortBy=top")
    public Call<CurrentNews> getNews();
//    public Call<CurrentNews> getNews(@Query("source") String sources,@Query("sortBy") String sortBy);
}
