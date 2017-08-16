package com.example.user.freshnews.screen.fragment.fragmentList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.freshnews.R;
import com.example.user.freshnews.data.provider.ContractClass;
import com.example.user.freshnews.screen.fragment.fragmentList.adapter.NewsCursorRecyclerAdapter;
import com.example.user.freshnews.screen.fragment.fragmentList.listener.NewsObserver;
import com.example.user.freshnews.utils.Const;


public class NewsListFragment extends Fragment implements NewsListContract.View, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private NewsListContract.Presenter presenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsCursorRecyclerAdapter adapter;
    private BroadcastReceiver br;
    private OnFragmentInteractionListener mListener;

    public NewsListFragment() {
    }

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        presenter = new NewsListPresenter(this, getContext().getContentResolver());
        initAdapter(savedInstanceState);
        initObservers();
        initView(view);
        return view;
    }

    private void initAdapter(Bundle usRemoteData) {
        boolean remoteData = false;
        if (usRemoteData == null) remoteData = true;
        adapter = new NewsCursorRecyclerAdapter(presenter.getRemoteNews(remoteData), getContext(), url ->
                presenter.startIntent(url));
    }

    private void initObservers() {
        // для обратной связи при загрузки или обновлении данных
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                //обработчик ответа из service
                presenter.handleStatus(intent.getIntExtra(Const.BroadcastConst.STATUS, 0));
            }
        };
        IntentFilter intFilt = new IntentFilter(Const.BroadcastConst.BROADCAST_ACTION);
        getContext().registerReceiver(br, intFilt);

        //для проверки количества записей в базе при каждом ее изменении
        NewsObserver ob = new NewsObserver(new Handler(), getContext().getContentResolver());
        getContext().getContentResolver().registerContentObserver(ContractClass.News.CONTENT_URI, true, ob);

    }

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showLoadingView() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoadingView() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void updateListView(Cursor news) {
        adapter.swapCursor(news);

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startIntent(String url) {
        if (mListener != null) {
            mListener.openDetails(url);
        }
    }

    @Override
    public void onRefresh() {
        presenter.showNews(presenter.getRemoteNews(false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(br);
        adapter.getCursor().close();
    }


    public interface OnFragmentInteractionListener {
        void openDetails(String url);
    }
}
