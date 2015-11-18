package com.itacit.healthcare.domain.interactor;

import rx.Subscriber;

/**
 * Created by root on 18.11.15.
 */
public abstract class UseCaseWithArgs<T, P> extends UseCase<T> {
    protected abstract void parseArgs(P... args);

    public void execute(Subscriber<T> subscriber, P... args) {
        parseArgs(args);
        super.execute(subscriber);
    }
}
