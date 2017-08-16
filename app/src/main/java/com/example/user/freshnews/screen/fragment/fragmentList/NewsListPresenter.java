package com.example.user.freshnews.screen.fragment.fragmentList;

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
    private boolean localStroge=false;

    public NewsListPresenter(NewsListContract.View view, ContentResolver contentResolver, boolean storage) {
        this.view = view;
        this.contentResolver = contentResolver;
        this.localStroge=storage;
    }

    @Override
    public void init() {

    }


    @Override
    public void startIntent(String url) {
        view.startIntent(url);
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
                view.showMessage("no update");
                break;
            case Const.BroadcastConst.STATUS_NOT_CONNECTION:
                view.hideLoadingView();
                view.showMessage("error connection");
                view.hideLoadingView();
                break;
            case Const.BroadcastConst.STATUS_FAILURE:
                view.hideLoadingView();
                view.showMessage("error connection");
                break;
        }

    }

    private Cursor getCursor(Uri URI) {
        if(localStroge){
            URI=ContractClass.News.CONTENT_URI;
            localStroge=false;
        }

        Cursor cursor= contentResolver.query(
                URI,
                ContractClass.News.DEFAULT_PROJECTION,
                null, null,
                ContractClass.News.DEFAULT_SORT_ORDER);


        return cursor;

    }
}
