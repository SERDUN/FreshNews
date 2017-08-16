package com.example.user.freshnews.screen.containerFragment;

import android.view.Window;

import com.example.user.freshnews.screen.BasePresenter;
import com.example.user.freshnews.screen.BaseView;

/**
 * Created by User on 16.08.2017.
 */

public class ContainerFragmentsContract {
    interface View extends BaseView {
        void openBrowser(String url);

        void showDetailsNews(String url);
    }

    interface Presenter extends BasePresenter {
        boolean checkExistenceView(Window v, int id);

        void saveUrlForDetailsNews(String url);

        String getUrlForDetailsNews();

        void loadDetailsMewsInTabletMode(boolean is);


    }
}
