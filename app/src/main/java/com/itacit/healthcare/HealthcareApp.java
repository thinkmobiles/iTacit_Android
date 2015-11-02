package com.itacit.healthcare;

import android.app.Application;
import android.content.Intent;

import com.itacit.healthcare.global.bus.RxBus;
import com.itacit.healthcare.global.errors.AuthError;
import com.itacit.healthcare.presentation.auth.AuthActivity;

import rx.Subscriber;

/**
 * Created by root on 14.10.15.
 */
public class HealthcareApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxBus.getInstance().toObserverable().subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if (o instanceof AuthError) {
                    Intent startActivity = new Intent();
                    startActivity.setClass(HealthcareApp.this, AuthActivity.class);
                    startActivity.setAction(AuthActivity.class.getName());
                    startActivity.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(startActivity);
                }
            }
        });
    }

}
