package com.example.user.freshnews.screen.containerFragment;

import android.content.SharedPreferences;
import android.view.Window;

import com.example.user.freshnews.utils.Const;

/**
 * Created by User on 16.08.2017.
 */

public class ContainerFragmentsPresenter implements ContainerFragmentsContract.Presenter {
    private boolean withDetails = true;
    private ContainerFragmentsContract.View view;
    private SharedPreferences preferences;
    private final String KEY_URL_OPEN_DETAILS = "keyUrlOpenDetails";

    public ContainerFragmentsPresenter(ContainerFragmentsContract.View view, boolean withDetails, SharedPreferences preferences) {
        this.withDetails = withDetails;
        this.view = view;
        this.preferences = preferences;
    }

    @Override
    public void init() {

    }

    @Override
    public boolean checkExistenceView(Window view, int id) {
        return (view.findViewById(id) != null);
    }

    @Override
    public void saveUrlForDetailsNews(String url) {
        preferences.edit().putString(Const.DETAILS_NEWS_PREF, url).apply();

    }

    @Override
    public String getUrlForDetailsNews() {
        return preferences.getString(Const.DETAILS_NEWS_PREF, null);
    }

    @Override
    public void loadDetailsMewsInTabletMode(boolean is) {
        if (is) view.showDetailsNews(getUrlForDetailsNews());
    }
}
