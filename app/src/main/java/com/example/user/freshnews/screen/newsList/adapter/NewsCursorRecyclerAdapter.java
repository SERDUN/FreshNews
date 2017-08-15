package com.example.user.freshnews.screen.newsList.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.freshnews.R;
import com.example.user.freshnews.data.provider.ContractClass;
import com.example.user.freshnews.screen.newsList.listener.ListenerNewsList;
import com.squareup.picasso.Picasso;

/**
 * Created by SERDUN on 09.08.2017.
 */

public class NewsCursorRecyclerAdapter extends CursorRecyclerAdapter<NewsCursorRecyclerAdapter.ViewHolder> {
    private Context context;
    private ListenerNewsList listenerNewsList;

    public NewsCursorRecyclerAdapter(Cursor c, Context context, ListenerNewsList listenerNewsList) {
        super(c);
        this.context = context;
        this.listenerNewsList = listenerNewsList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.bind(cursor, context, listenerNewsList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imageView;
        TextView tvDescription;
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            imageView = (ImageView) itemView.findViewById(R.id.iv_news);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);

        }

        public void bind(@NonNull Cursor cursor, Context context, ListenerNewsList listenerNewsList) {
            tvTitle.setText(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_TITLE)));
            tvDescription.setText(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_DESCRIPTION)));
            tvDate.setText(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_PUBLISHED_AT)));
            Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_URL_TO_IMAGE))).into(imageView);

            itemView.setOnClickListener(v -> listenerNewsList.onItemClick(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_URL))));
        }
    }
}
