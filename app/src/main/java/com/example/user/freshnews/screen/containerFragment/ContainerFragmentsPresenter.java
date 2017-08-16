package com.example.user.freshnews.screen.containerFragment;

import android.view.Window;

/**
 * Created by User on 16.08.2017.
 */

public class ContainerFragmentsPresenter implements ContainerFragmentsContract.Presenter {
    private boolean withDetails = true;
    private ContainerFragmentsContract.View view;

    public ContainerFragmentsPresenter(ContainerFragmentsContract.View view, boolean withDetails) {
        this.withDetails = withDetails;
        this.view = view;
    }

    @Override
    public void init() {

    }

    @Override
    public boolean checkExistenceView(Window view, int id) {
        return (view.findViewById(id) != null);
    }

    @Override
    public void useTabletMode(boolean is) {
        if (is) view.showDetailsNews();
    }
}
