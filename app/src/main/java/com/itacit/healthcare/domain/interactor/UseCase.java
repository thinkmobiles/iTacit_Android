/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.itacit.healthcare.domain.interactor;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link Subscriber}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCase<T, P, A> {


    private Subscription subscription = Subscriptions.empty();

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    protected abstract Observable<T> buildUseCaseObservable(A obsArgs);
    protected abstract @NonNull A initArgs(P args);


    public final void execute(Subscriber<T> subscriber, P args) {
      this.subscription = this.buildUseCaseObservable(initArgs(args))
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(subscriber);
    }

    public final void execute(Action1<T> action, Action1<Throwable> errorHandler, P args) {
        this.subscription = this.buildUseCaseObservable(initArgs(args))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        errorHandler.call(e);
                    }

                    @Override
                    public void onNext(T t) {
                        action.call(t);
                    }
                });
    }

    public final void execute(Action1<T> action, Action1<Throwable> errorHandler) {
        execute(action, errorHandler, null);
    }


    /**
     * Executes the current use case.
     *
     * @param useCaseSubscriber The guy who will be listen to the observable build with {@link #buildUseCaseObservable(A obsArgs)}.
     */
    @SuppressWarnings("unchecked")
    protected final void execute(Subscriber<T> useCaseSubscriber, Scheduler subscribeOn, Scheduler observeOn) {
      this.subscription = this.buildUseCaseObservable(initArgs(null))
          .subscribeOn(subscribeOn)
          .observeOn(observeOn)
          .subscribe(useCaseSubscriber);
    }


    /**
     * Unsubscribes from current {@link Subscription}.
     */
    public void unsubscribe() {
      if (!subscription.isUnsubscribed()) {
        subscription.unsubscribe();
      }
    }
}
