package com.itacit.healthcare.domain.interactor;

import rx.Subscriber;

/**
 * Created by root on 20.10.15.
 */
public interface Interactor<T> {
    public void execute(Subscriber<T> subscriber);
    public void unsubscribe();
}
