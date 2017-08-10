package com.example.user.freshnews.data.database;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.user.freshnews.adapter.CursorRecyclerAdapter;

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

    public NewsObserver(Handler handler, RecyclerView.Adapter adapter, ContentResolver resolver) {
        super(handler);
        this.adapter = adapter;
        this.resolver = resolver;
    }

    @Override
    public void onChange(boolean selfChange) {
        this.onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        Log.d("myLog", "onChange2: ");
        updateCursor();
        // do s.th.
        // depending on the handler you might be on the UI
        // thread, so be cautious!
    }

    private void updateCursor() {

        Cursor c = resolver.query(
                ContractClass.News.CONTENT_URI,
                ContractClass.News.DEFAULT_PROJECTION,
                null, null,
                null);

        ((CursorRecyclerAdapter) adapter).swapCursor(c);
    }
}
