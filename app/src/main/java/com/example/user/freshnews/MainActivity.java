package com.example.user.freshnews;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.freshnews.data.database.ContractClass;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "log_my";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor c = getContentResolver().query(
                ContractClass.News.CONTENT_URI,
                ContractClass.News.DEFAULT_PROJECTION,
                null, null,
                null);

        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);

                } while (c.moveToNext());
            }
            c.close();
        } else
            Log.d(LOG_TAG, "Cursor is null");

    }
}
