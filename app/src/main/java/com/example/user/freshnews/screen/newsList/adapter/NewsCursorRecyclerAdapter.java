package com.example.user.freshnews.screen.newsList.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.freshnews.R;
import com.example.user.freshnews.data.provider.ContractClass;
import com.squareup.picasso.Picasso;

/**
 * Created by SERDUN on 09.08.2017.
 */

public class NewsCursorRecyclerAdapter extends CursorRecyclerAdapter<NewsCursorRecyclerAdapter.ViewHolder> {
    Context context;

    public NewsCursorRecyclerAdapter(Cursor c, Context context) {
        super(c);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.tvTitle.setText(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_TITLE)));
        holder.tvDescription.setText(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_DESCRIPTION)));
        holder.tvDate.setText(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_PUBLISHED_AT)));
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(ContractClass.News.COLUMN_NAME_URL_TO_IMAGE))).into(holder.imageView);

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
    }
}
