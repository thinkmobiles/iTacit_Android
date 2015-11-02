package com.itacit.healthcare.presentation.base.presenters;

import android.support.annotation.Nullable;

import com.itacit.healthcare.presentation.base.views.IView;

import java.lang.ref.WeakReference;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by root on 13.10.15.
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {
    private WeakReference<V> viewRef;
    protected CompositeSubscription compositeSubscription;

    @Override
    public final void attachView(V view) {
        if(compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        viewRef = new WeakReference<>(view);
        onViewAttach();
    }

    @Override
    public void detachView() {
        onViewDetach();
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }

        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    protected void onViewAttach() {}
    protected void onViewDetach() {}
}
