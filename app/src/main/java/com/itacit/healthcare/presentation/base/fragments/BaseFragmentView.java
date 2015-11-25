package com.itacit.healthcare.presentation.base.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.base.presenters.Presenter;
import com.itacit.healthcare.presentation.base.views.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by root on 13.10.15.
 */
public abstract class BaseFragmentView<P extends Presenter, A extends BaseActivity> extends Fragment implements View {

    public TextView tvIsEmpty;

    @Inject
    protected P presenter;
    private ActionBarDrawerToggle toggle;
    protected A activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (A) getActivity();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this, view);
        tvIsEmpty = (TextView) view.findViewById(R.id.tv_rv_is_empty);
        return view;
    }

    protected void switchToolbarIndicator(boolean enable, @Nullable android.view.View.OnClickListener listener) {
        toggle.setDrawerIndicatorEnabled(enable);
        toggle.setToolbarNavigationClickListener(listener);
    }

    @Override
    public void onViewCreated(android.view.View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (presenter == null) {
            presenter = createPresenter();
        }

        presenter.attachView(this);
        ActionBar actionBar = activity.getSupportActionBar();
        if (toggle == null) {
            toggle = activity.getToggle();
        }
        setUpActionBar(actionBar);
        setUpView();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        ButterKnife.unbind(this);
    }

    @Override
    public Scheduler getUiThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    protected abstract void setUpView();
    protected abstract void setUpActionBar(ActionBar actionBar);
    protected abstract @LayoutRes int getLayoutRes();
    protected abstract P createPresenter();
}
