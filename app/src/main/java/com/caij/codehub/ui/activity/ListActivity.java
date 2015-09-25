package com.caij.codehub.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.caij.codehub.R;
import com.caij.codehub.presenter.BasePresent;
import com.caij.codehub.ui.adapter.BaseAdapter;
import com.caij.codehub.widgets.LoadMoreListView;

import butterknife.Bind;

/**
 * Created by Caij on 2015/9/24.
 */
public abstract class ListActivity<P extends BasePresent, AP extends BaseAdapter> extends BaseCodeHubActivity<P> implements LoadMoreListView.OnLoadMoreListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    protected AP mAdapter;
    @Bind(R.id.list_view)
    LoadMoreListView mListView;
    @Override

    protected int getContentLayoutId() {
        return R.layout.list_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = createAdapter();
        mListView.setOnLoadMoreListener(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));

        content.setVisibility(View.GONE);
    }

    protected abstract AP createAdapter();

    public LoadMoreListView getListView() {
        return mListView;
    }
}
