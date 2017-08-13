package com.example.user.freshnews.screen.newsList.listener;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by User on 10.08.2017.
 */

public class NewsObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */

    private RecyclerView.Adapter adapter;
    private ContentResolver resolver;
    private NewsObserverCallback newsObserverCallback;

    public NewsObserver(Handler handler,NewsObserverCallback newsObserverCallback,RecyclerView.Adapter adapter, ContentResolver resolver) {
        super(handler);
        this.newsObserverCallback=newsObserverCallback;
        this.adapter = adapter;
        this.resolver = resolver;
    }

    @Override
    public void onChange(boolean selfChange) {
        this.onChange(selfChange, null);
        Log.d("ddfdf", "onChange: ");
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        updateCursor();
        newsObserverCallback.onChange();

    }

    private void updateCursor() {


    }
}
