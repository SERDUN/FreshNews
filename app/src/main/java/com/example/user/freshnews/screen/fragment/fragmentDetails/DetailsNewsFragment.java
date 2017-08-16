package com.example.user.freshnews.screen.fragment.fragmentDetails;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.user.freshnews.R;

public class DetailsNewsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private ProgressBar pbLoadingPage;
    private WebView mWebView;
    private String url = null;

    public static DetailsNewsFragment newInstance(String url) {
        DetailsNewsFragment fragment = new DetailsNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
        }
    }


    public String getUrl() {
        return url;
    }

    public DetailsNewsFragment() {
    }

    public static DetailsNewsFragment newInstance() {
        DetailsNewsFragment fragment = new DetailsNewsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deatils_news, container, false);
        initView(view);
        loadPage();
        return view;
    }


    public void initView(View view) {
        mWebView = (WebView) view.findViewById(R.id.web);
        mWebView.getSettings().setJavaScriptEnabled(true);
        pbLoadingPage = (ProgressBar) view.findViewById(R.id.pb_loading_page);
        mWebView.setWebViewClient(new MyWebViewClient());

    }

    private void loadPage() {
        if (url != null)
            mWebView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pbLoadingPage.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbLoadingPage.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
