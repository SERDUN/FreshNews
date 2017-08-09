package com.example.user.freshnews.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.freshnews.R;

/**
 * Created by User on 09.08.2017.
 */

public class NewsCursorRecyclerAdapter extends CursorRecyclerAdapter {

    public NewsCursorRecyclerAdapter(Cursor c) {
        super(c);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.news_item);

        }
    }
    }
