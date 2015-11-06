package com.caij.codehub.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.VolleyError;
import com.caij.codehub.R;
import com.caij.codehub.bean.Entity;
import com.caij.codehub.presenter.Present;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Caij on 2015/11/4.
 */
public abstract class SwipeRefreshRecyclerViewFragment<E extends Entity> extends RecyclerViewFragment<E> implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getContentLayoutId() {
        return R.layout.include_refresh_recycle_view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
    }

    @Override
    public void onFirstLoadSuccess(List<E> entities) {
        showContentContainer();
        getRecyclerViewAdapter().setEntities(entities);
        getRecyclerViewAdapter().notifyDataSetChanged();
    }

    @Override
    public void onRefreshSuccess(List<E> entities) {
        mSwipeRefreshLayout.setRefreshing(false);
        getRecyclerViewAdapter().setEntities(entities);
        getRecyclerViewAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreSuccess(List<E>  entities) {
        getLoadMoreRecyclerView().completeLoading();
        getRecyclerViewAdapter().addEntities(entities);
        getRecyclerViewAdapter().notifyDataSetChanged();
    }

    @Override
    public void onRefreshError(VolleyError error) {
        mSwipeRefreshLayout.setRefreshing(false);
        processVolleyError(error);
    }

    @Override
    public void onLoadMoreError(VolleyError error) {
        getLoadMoreRecyclerView().completeLoading();
        processVolleyError(error);
    }

    @Override
    public void onFirstLoadError(VolleyError error) {
        processVolleyError(error);
        showError();
    }

    @Override
    public void onLoadMore() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoading(int loadType) {
        if (loadType == Present.LoadType.FIRSTLOAD) {
            showLoading();
        }else if (loadType == Present.LoadType.REFRESH) {
        }else if (loadType == Present.LoadType.LOADMOER) {
        }
    }

    @Override
    public void onLoaded(int loadType) {
        if (loadType == Present.LoadType.FIRSTLOAD) {
            hideLoading();
        }else if (loadType == Present.LoadType.REFRESH) {
        }else if (loadType == Present.LoadType.LOADMOER) {
        }
    }

}