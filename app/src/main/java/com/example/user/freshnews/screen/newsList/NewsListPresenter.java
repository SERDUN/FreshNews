package com.example.user.freshnews.screen.newsList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.user.freshnews.data.provider.ContractClass;
import com.example.user.freshnews.utils.Const;

/**
 * Created by User on 13.08.2017.
 */

public class NewsListPresenter implements NewsListContract.Presenter {
    private NewsListContract.View view;
    private ContentResolver contentResolver;

    public NewsListPresenter(NewsListContract.View view, ContentResolver contentResolver) {
        this.view = view;
        this.contentResolver = contentResolver;
    }

    @Override
    public void init() {

    }


    @Override
    public Cursor getCachedNews() {
        return getCursor(
                ContractClass.News.CONTENT_URI_SERVER);

    }

    @Override
    public void showNews(Cursor cursor) {
        view.updateListView(cursor);
    }

    @Override
    public void handleStatus(int status) {
        switch (status) {
            case Const.BroadcastConst.STATUS_OK:
                view.updateListView(getCursor(ContractClass.News.CONTENT_URI));
                view.hideLoadingView();
                break;
            case Const.BroadcastConst.STATUS_NOT_UPDATE:
                view.hideLoadingView();

                break;
            case Const.BroadcastConst.STATUS_NOT_CONNECTION:
                view.hideLoadingView();

                break;
            case Const.BroadcastConst.STATUS_FAILURE:
                view.hideLoadingView();

                break;

        }

    }

    private Cursor getCursor(Uri URI) {
        return contentResolver.query(
                URI,
                ContractClass.News.DEFAULT_PROJECTION,
                null, null,
                null);

    }
}