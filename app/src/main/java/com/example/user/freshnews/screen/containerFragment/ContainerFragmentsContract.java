package com.example.user.freshnews.screen.containerFragment;

import android.view.Window;

import com.example.user.freshnews.screen.BasePresenter;
import com.example.user.freshnews.screen.BaseView;

/**
 * Created by User on 16.08.2017.
 */

public class ContainerFragmentsContract {
    interface View extends BaseView {
        void showDetailsNews();
    }

    interface Presenter extends BasePresenter {
        boolean checkExistenceView(Window v, int id);

        void useTabletMode(boolean is);
    }
}
