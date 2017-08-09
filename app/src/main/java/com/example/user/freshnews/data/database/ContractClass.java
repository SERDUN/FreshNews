package com.example.user.freshnews.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 09.08.2017.
 */

public class ContractClass {
    public static final String AUTHORITY = "com.example.user.freshnews.data.database.ContractClass";

    private ContractClass() {
    }

    public static final class News implements BaseColumns {
        private News() {
        }

        public static final String TABLE_NAME = "news";
        private static final String SCHEME = "content://";
        private static final String PATH_NEWS = "/news";
        private static final String PATH_NEWS_ID = "/news/";
        public static final int NEWS_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_NEWS);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_NEWS_ID);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.news";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.news";
        // public static final String DEFAULT_SORT_ORDER = "second_name ASC";

        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_NAME_PUBLISHED_AT = "publishedAt";

        public static final String[] DEFAULT_PROJECTION = new String[]{
                ContractClass.News._ID,
                ContractClass.News.COLUMN_NAME_AUTHOR,
                ContractClass.News.COLUMN_NAME_TITLE,
                ContractClass.News.COLUMN_NAME_DESCRIPTION,
                ContractClass.News.COLUMN_NAME_URL,
                ContractClass.News.COLUMN_NAME_URL_TO_IMAGE,
                ContractClass.News.COLUMN_NAME_PUBLISHED_AT
        };


    }


}
