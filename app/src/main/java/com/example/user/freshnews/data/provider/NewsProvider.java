package com.example.user.freshnews.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.user.freshnews.data.service.ServiceHelper;

import java.util.HashMap;

/**
 * Created by SERDUN on 09.08.2017.
 */

public class NewsProvider extends ContentProvider {
    //// TODO: 09.08.2017 create base abstract class
    private static final int DATABASE_VERSION = 1;

    private static HashMap<String, String> newsProjectionMap;

    private static final int NEWS = 1;
    private static final int NEWS_ID = 2;
    private static final int NEWS_SERVER = 3;

    private static final UriMatcher uriMatcher;
    private DatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ContractClass.AUTHORITY, "news", NEWS);
        uriMatcher.addURI(ContractClass.AUTHORITY, "news/server", NEWS_SERVER);
        uriMatcher.addURI(ContractClass.AUTHORITY, "news/#", NEWS_ID);

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
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = null;
        switch (uriMatcher.match(uri)) {
            case NEWS_SERVER:
                ServiceHelper.startQueryService(getContext());
            case NEWS:
                qb.setTables(ContractClass.News.TABLE_NAME);
                qb.setProjectionMap(newsProjectionMap);
//                orderBy = ContractClass.News.DEFAULT_SORT_ORDER;
                break;
            case NEWS_ID:
                qb.setTables(ContractClass.News.TABLE_NAME);
                qb.setProjectionMap(newsProjectionMap);
                qb.appendWhere(ContractClass.News._ID + "=" + uri.getPathSegments().get(ContractClass.News.NEWS_ID_PATH_POSITION));
//                orderBy = ContractClass.News.DEFAULT_SORT_ORDER;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case NEWS:
                return ContractClass.News.CONTENT_TYPE;
            case NEWS_ID:
                return ContractClass.News.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        if (uriMatcher.match(uri) != NEWS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        int nrInserted = 0;
        long rowId = -1;
        Uri rowUri = Uri.EMPTY;


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {

            for (ContentValues cv : values) {

//                db.insertOrThrow(ContractClass.News.TABLE_NAME, null, cv);

                rowId = db.insertOrThrow(ContractClass.News.TABLE_NAME, null, cv);

                nrInserted++;
            }

            db.setTransactionSuccessful();
            db.endTransaction();


        } catch (SQLException ex) {

            ex.printStackTrace();

        } finally {

        }

        if (rowId > 0) {

//            rowUri = ContentUris.withAppendedId(ContractClass.News.CONTENT_ID_URI_BASE, rowId);
            rowUri = ContentUris.withAppendedId(ContractClass.News.CONTENT_ID_URI_BASE, rowId);
            getContext().getContentResolver().notifyChange(rowUri, null);
        }
        return nrInserted;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues newValues) {

        if (uriMatcher.match(uri) != NEWS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values;
        if (newValues != null) {
            values = new ContentValues(newValues);
        } else {
            values = new ContentValues();
        }
        long rowId = -1;
        Uri rowUri = Uri.EMPTY;

        if (uriMatcher.match(uri) == NEWS) {
            if (values.containsKey(ContractClass.News.COLUMN_NAME_AUTHOR) == false) {
                values.put(ContractClass.News.COLUMN_NAME_AUTHOR, "");
            }

            if (values.containsKey(ContractClass.News.COLUMN_NAME_TITLE) == false) {
                values.put(ContractClass.News.COLUMN_NAME_TITLE, "");
            }

            if (values.containsKey(ContractClass.News.COLUMN_NAME_DESCRIPTION) == false) {
                values.put(ContractClass.News.COLUMN_NAME_DESCRIPTION, "");
            }

            if (values.containsKey(ContractClass.News.COLUMN_NAME_URL) == false) {
                values.put(ContractClass.News.COLUMN_NAME_URL, "");
            }

            if (values.containsKey(ContractClass.News.COLUMN_NAME_URL_TO_IMAGE) == false) {
                values.put(ContractClass.News.COLUMN_NAME_URL_TO_IMAGE, "");
            }

            if (values.containsKey(ContractClass.News.COLUMN_NAME_PUBLISHED_AT) == false) {
                values.put(ContractClass.News.COLUMN_NAME_PUBLISHED_AT, "");
            }


            rowId = db.insert(ContractClass.News.TABLE_NAME, null, values);

            Log.d("item_size", "insert: " + rowId);

            if (rowId > 0) {
                rowUri = ContentUris.withAppendedId(ContractClass.News.CONTENT_ID_URI_BASE, rowId);
                getContext().getContentResolver().notifyChange(rowUri, null);
            }
        }


        return rowUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[]
            selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String finalWhere;
        int count;
        switch (uriMatcher.match(uri)) {
            case NEWS:
                count = db.delete(ContractClass.News.TABLE_NAME, selection, selectionArgs);
                break;
            case NEWS_ID:
                finalWhere = ContractClass.News._ID + " = " + uri.getPathSegments().get(ContractClass.News.NEWS_ID_PATH_POSITION);
                if (selection != null) {
                    finalWhere = finalWhere + " AND " + selection;
                }
                count = db.delete(ContractClass.News.TABLE_NAME, finalWhere, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        String finalWhere;
        String id;
        switch (uriMatcher.match(uri)) {
            case NEWS:
                count = db.update(ContractClass.News.TABLE_NAME, values, where, whereArgs);
                break;
            case NEWS_ID:
                id = uri.getPathSegments().get(ContractClass.News.NEWS_ID_PATH_POSITION);
                finalWhere = ContractClass.News._ID + " = " + id;
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(ContractClass.News.TABLE_NAME, values, finalWhere, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
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

        ////     TODO: 09.08.2017  Change the data type KEY_NAME_PUBLISHED_AT from text to data

        private Context ctx;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            ctx = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_TABLE_NEWS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NEWS);
            onCreate(db);
        }
    }

}
