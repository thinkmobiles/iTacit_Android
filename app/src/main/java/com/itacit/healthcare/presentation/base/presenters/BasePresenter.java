package com.itacit.healthcare.presentation.base.presenters;

import com.itacit.healthcare.presentation.base.views.IView;

import java.lang.ref.WeakReference;

/**
 * Created by root on 13.10.15.
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {
    private WeakReference<V> mView;

    @Override
    public void attachView(V view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        mView.clear();
    }
}
