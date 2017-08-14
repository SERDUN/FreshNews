package com.example.user.freshnews.screen.newsList.listener;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.example.user.freshnews.data.provider.ContractClass;

/**
 * Created by User on 10.08.2017.
 */

public class NewsObserver extends ContentObserver {
    public final static int COUNT_SAVE_ELEMENT = 30;
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */

    private ContentResolver resolver;

    public NewsObserver(Handler handler, ContentResolver resolver) {
        super(handler);
        this.resolver = resolver;
    }


    @Override
    public void onChange(boolean selfChange, Uri uri) {
        Cursor c = resolver.query(
                ContractClass.News.CONTENT_URI,
                ContractClass.News.DEFAULT_PROJECTION,
                null, null,
                null);


        //если елементов больше 30 удаляем остальное
     if (c != null) {
            if (c.getCount() > COUNT_SAVE_ELEMENT) {
                String ob="_id IN (SELECT _id FROM "+ContractClass.News.TABLE_NAME+" ORDER BY _id ASC LIMIT "+(c.getCount()-COUNT_SAVE_ELEMENT)+")";
                 resolver.delete(ContractClass.News.CONTENT_URI, ob, null);
        }}
    }
}



