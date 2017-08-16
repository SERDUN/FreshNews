package com.example.user.freshnews.screen.containerFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.freshnews.R;


public class ContainerFragment extends AppCompatActivity implements ContainerFragmentContract.View {
    ContainerFragmentContract.Presenter presenter;
    boolean withDetails = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new ContainerFragmentPresenter(this, true, "");
//        presenter.checkExistenceView(getWindow(),R.id.newsDetail);


    }


    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }
}

