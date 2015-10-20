package com.itacit.healthcare.presentation.base.presenters;

import android.support.annotation.Nullable;

import com.itacit.healthcare.presentation.base.views.IView;

import java.lang.ref.WeakReference;

/**
 * Created by root on 13.10.15.
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {
    private WeakReference<V> mViewRef;

    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    @Nullable
    public V getView() {
        return mViewRef == null ? null : mViewRef.get();
    }
}
