package com.itacit.healthcare.presentation.base.presenters;

import com.itacit.healthcare.presentation.base.views.IView;

/**
 * Created by root on 13.10.15.
 */
public interface IPresenter<V extends IView> {

    public void attachView(V view);
    public void detachView();
}
