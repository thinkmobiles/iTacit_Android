package com.itacit.healthcare.presentation.base.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itacit.healthcare.presentation.base.presenters.IPresenter;

/**
 * Created by root on 13.10.15.
 */
public abstract class BaseFragmentView<P extends IPresenter> extends Fragment implements IView {
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }


    protected abstract @LayoutRes int getLayoutRes();
    protected abstract P createPresenter();
}
