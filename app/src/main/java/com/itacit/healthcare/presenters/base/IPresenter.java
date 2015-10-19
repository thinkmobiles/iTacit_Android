package com.itacit.healthcare.presenters.base;

import com.itacit.healthcare.views.IView;

/**
 * Created by root on 13.10.15.
 */
public interface IPresenter<V extends IView> {

    public void attachView(V view);
    public void detachView();
}
