package com.example.user.freshnews.screen.containerFragment;

import android.view.Window;

/**
 * Created by User on 16.08.2017.
 */

public class ContainerFragmentPresenter implements ContainerFragmentContract.Presenter {
    private boolean withDetails = true;
    private String url = null;
    private ContainerFragmentContract.View view;

    public ContainerFragmentPresenter(ContainerFragmentContract.View view, boolean withDetails, String url) {
        this.withDetails = withDetails;
        this.url = url;
        this.view = view;
    }

    @Override
    public void init() {

    }

    @Override
    public boolean checkExistenceView(Window view, int id) {
        return (view.findViewById(id) != null);
    }
}
