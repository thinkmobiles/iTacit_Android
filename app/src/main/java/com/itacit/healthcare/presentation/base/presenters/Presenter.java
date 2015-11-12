package com.itacit.healthcare.presentation.base.presenters;

import com.itacit.healthcare.presentation.base.views.View;

/**
 * Created by root on 13.10.15.
 */
public interface Presenter<V extends View> {

    void attachView(V view);
    void detachView();
}
