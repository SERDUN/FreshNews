package com.example.user.freshnews.data.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by User on 09.08.2017.
 */

public class NewsProvider extends ContentProvider {
    //// TODO: 09.08.2017 create base abstract class 
    private static final int DATABASE_VERSION = 1;

    private static HashMap<String, String> newsProjectionMap;

    private static final int NEWS = 1;
    private static final int NEWS_ID = 2;

    private static final UriMatcher uriMatcher;
    private DatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ContractClass.AUTHORITY, "students", NEWS);
        uriMatcher.addURI(ContractClass.AUTHORITY, "students/#", NEWS_ID);

        newsProjectionMap = new HashMap<String, String>();
        for (int i = 0; i < ContractClass.News.DEFAULT_PROJECTION.length; i++) {
            newsProjectionMap.put(
                    ContractClass.News.DEFAULT_PROJECTION[i],
                    ContractClass.News.DEFAULT_PROJECTION[i]);
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        //// TODO: 09.08.2017 create const helper
        private static final String DATABASE_NAME = "NewsDatabase";
        public static final String DATABASE_TABLE_NEWS = ContractClass.News.TABLE_NAME;
        public static final String KEY_ROWID = "_id";
        public static final String KEY_NAME_AUTHOR = "author";
        public static final String KEY_NAME_TITLE = "title";
        public static final String KEY_NAME_DESCRIPTION = "description";
        public static final String KEY_NAME_URL = "url";
        public static final String KEY_NAME_URL_TO_IMAGE = "urlToImage";
        public static final String KEY_NAME_PUBLISHED_AT = "publishedAt";

        private static final String DATABASE_CREATE_TABLE_NEWS =
                "create table " + DATABASE_TABLE_NEWS + " ("
                        + KEY_ROWID + " integer primary key autoincrement, "
                        + KEY_NAME_AUTHOR + " string , "
                        + KEY_NAME_TITLE + " string , "
                        + KEY_NAME_DESCRIPTION + " string , "
                        + KEY_NAME_URL + " string , "
                        + KEY_NAME_URL_TO_IMAGE + " string , "
                        + KEY_NAME_PUBLISHED_AT + " string, "
                        + " UNIQUE ( " + KEY_NAME_DESCRIPTION + " ) ON CONFLICT IGNORE" + ");";

        //// TODO: 09.08.2017  Change the data type KEY_NAME_PUBLISHED_AT from text to data

        private Context ctx;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            ctx = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_TABLE_NEWS);
            db.execSQL("insert into news values (null, 'auth test1', 'Title test', 'description', 'URL', 'IMG_IRL', 'DATA');");
            db.execSQL("insert into news values (null, 'auth test2', 'Title test', 'description', 'URL', 'IMG_IRL', 'DATA');");
            db.execSQL("insert into news values (null, 'auth tes3t', 'Title test', 'description', 'URL', 'IMG_IRL', 'DATA');");
            db.execSQL("insert into news values (null, 'auth test4', 'Title test', 'description', 'URL', 'IMG_IRL', 'DATA');");
            db.execSQL("insert into news values (null, 'auth test5', 'Title test', 'description', 'URL', 'IMG_IRL', 'DATA');");
            db.execSQL("insert into news values (null, 'auth test6', 'Title test', 'description', 'URL', 'IMG_IRL', 'DATA');");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NEWS);
            onCreate(db);
        }
    }

}
