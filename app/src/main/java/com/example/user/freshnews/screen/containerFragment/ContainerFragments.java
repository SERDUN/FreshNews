package com.example.user.freshnews.screen.containerFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.freshnews.R;
import com.example.user.freshnews.screen.fragment.fragmentDetails.DetailsNewsFragment;


public class ContainerFragments extends AppCompatActivity implements ContainerFragmentsContract.View {
    ContainerFragmentsContract.Presenter presenter;
    boolean withDetails = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new ContainerFragmentsPresenter(this, withDetails);
        presenter.useTabletMode(presenter.checkExistenceView(getWindow(), R.id.fm_details_news));


    }


    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showDetailsNews() {
        if (presenter.checkExistenceView(getWindow(), R.id.fm_details_news)) {
            DetailsNewsFragment details = (DetailsNewsFragment) getSupportFragmentManager().findFragmentById(R.id.fm_details_news);
            int e = 3;
        }


    }
}

