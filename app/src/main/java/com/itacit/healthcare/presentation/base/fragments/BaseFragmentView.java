package com.itacit.healthcare.presentation.base.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.base.presenters.IPresenter;
import com.itacit.healthcare.presentation.base.views.IView;

import butterknife.ButterKnife;

/**
 * Created by root on 13.10.15.
 */
public abstract class BaseFragmentView<P extends IPresenter, A extends BaseActivity> extends Fragment implements IView {
    protected P presenter;
    private ActionBarDrawerToggle toggle;
    protected A activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (A) getActivity();
        toggle = activity.getToggle();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected void switchToolbarIndicator(boolean enable, @Nullable View.OnClickListener listener) {
        toggle.setDrawerIndicatorEnabled(enable);
        toggle.setToolbarNavigationClickListener(listener);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpActionBar(activity.getSupportActionBar());
        setUpView();
        if (presenter == null) {
            presenter = createPresenter();
        }
        presenter.attachView(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        ButterKnife.unbind(this);
    }

    protected abstract void setUpView();
    protected abstract void setUpActionBar(ActionBar actionBar);
    protected abstract @LayoutRes int getLayoutRes();
    protected abstract P createPresenter();
}
