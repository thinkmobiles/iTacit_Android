package com.itacit.healthcare.presentation.base.presenters;

import android.support.annotation.Nullable;

import com.itacit.healthcare.presentation.base.views.View;

import java.lang.ref.WeakReference;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by root on 13.10.15.
 */
public abstract class BasePresenter<V extends View> implements Presenter<V> {
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

    public void actOnView(ActionOnView<V> action) {
        if (getView() != null) action.act(getView());
    }

    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    protected void onViewAttach() {}
    protected void onViewDetach() {}

    public interface ActionOnView<V> {
        void act(V view);
    }
}
