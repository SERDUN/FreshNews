package com.example.user.freshnews.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.freshnews.R;
import com.example.user.freshnews.data.database.ContractClass;

/**
 * Created by SERDUN on 09.08.2017.
 */

public class NewsCursorRecyclerAdapter extends CursorRecyclerAdapter<NewsCursorRecyclerAdapter.ViewHolder> {
    public NewsCursorRecyclerAdapter(Cursor c) {
        super(c);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.name.setText(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_TITLE)));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.news_item);

        }
    }
}
