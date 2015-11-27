package com.itacit.healthcare.presentation.base.views;

import rx.Scheduler;

/**
 * Created by root on 13.10.15.
 */
public interface View {
    Scheduler getUiThreadScheduler();
    void hideProgress();
    void showProgress();
    void showError(String error);
}
