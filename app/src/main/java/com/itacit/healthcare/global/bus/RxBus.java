package com.itacit.healthcare.global.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by root on 02.11.15.
 */
public class RxBus {
    private static RxBus rxBus;
    public static RxBus getInstance() {
        if (rxBus == null) rxBus = new RxBus();
        return rxBus;
    }

    private RxBus(){}

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }
}
