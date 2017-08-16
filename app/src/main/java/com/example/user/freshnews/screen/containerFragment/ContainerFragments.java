package com.example.user.freshnews.screen.containerFragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.freshnews.R;
import com.example.user.freshnews.screen.fragment.fragmentDetails.DetailsNewsFragment;
import com.example.user.freshnews.screen.fragment.fragmentList.NewsListFragment;
import com.example.user.freshnews.utils.Const;


public class ContainerFragments extends AppCompatActivity implements ContainerFragmentsContract.View, NewsListFragment.OnFragmentInteractionListener {
    ContainerFragmentsContract.Presenter presenter;
    boolean withDetails = true;
    TextView tvNewsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences(Const.DETAILS_NEWS_PREF, MODE_PRIVATE);
        presenter = new ContainerFragmentsPresenter(this, withDetails, preferences);
        initView();
    }

    private void initView() {
        tvNewsMessage = (TextView) findViewById(R.id.tv_news_message);

    }


    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }


    @Override
    public void startIntent(String url) {
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
        if (presenter.checkExistenceView(getWindow(), R.id.fl_container)) {
            DetailsNewsFragment details = (DetailsNewsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fl_container);
            if (details == null || details.getUrl() != presenter.getUrlForDetailsNews()) {
                details = DetailsNewsFragment.newInstance(presenter.getUrlForDetailsNews());
                tvNewsMessage.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, details).commit();

            }

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

    }
}

