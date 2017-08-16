package com.example.user.freshnews.screen.containerFragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.user.freshnews.R;
import com.example.user.freshnews.screen.fragment.fragmentDetails.DetailsNewsFragment;
import com.example.user.freshnews.screen.fragment.fragmentList.NewsListFragment;


public class ContainerFragments extends AppCompatActivity implements ContainerFragmentsContract.View, NewsListFragment.OnFragmentInteractionListener {
    private ContainerFragmentsContract.Presenter presenter;
    private DetailsNewsFragment details;
    private boolean withDetails = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new ContainerFragmentsPresenter(this, withDetails);
    }


    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }


    @Override
    public void openBrowser(String url) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    public void showDetailsNews(String url) {
        if (details == null)
            details = (DetailsNewsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fl_container);

        if (!details.getUrl().equals(url) || details.getUrl().isEmpty()) {
            details.setUrl(url);
            details.loadPage();
        }

    }

    @Override
    public void openDetails(String url) {
        presenter.saveUrlForDetailsNews(url);
        presenter.loadDetailsMewsInTabletMode(presenter.checkExistenceView(getWindow(), R.id.fl_container));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter.checkExistenceView(getWindow(), R.id.fl_container))
            details = null;
    }
}

