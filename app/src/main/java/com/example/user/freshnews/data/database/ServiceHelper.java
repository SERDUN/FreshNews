package com.example.user.freshnews.data.database;

import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 10.08.2017.
 */

public class ServiceHelper {
    public static void startQueryService(Context context) {
        context.startService(new Intent(context, NewsService.class));
    }
}
