package com.example.user.freshnews.screen.fragment.fragmentList;

import android.database.Cursor;

import com.example.user.freshnews.screen.BasePresenter;
import com.example.user.freshnews.screen.BaseView;

/**
 * Created by User on 13.08.2017.
 */

public class NewsListContract {
    interface View extends BaseView {
        public void updateListView(Cursor cursor);

        public void showMessage(String msg);

        public void startIntent(String uri);

    }

    interface Presenter extends BasePresenter {
        public void startIntent(String uri);

        public Cursor getCachedNews();

        public void showNews(Cursor cursor);

        public void handleStatus(int status);

    }
}
