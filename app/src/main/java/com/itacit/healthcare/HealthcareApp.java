package com.itacit.healthcare;

import android.app.Application;
import android.content.Intent;

import com.itacit.healthcare.global.bus.RxBus;
import com.itacit.healthcare.global.errors.AuthError;
import com.itacit.healthcare.presentation.auth.AuthActivity;

/**
 * Created by root on 14.10.15.
 */
public class HealthcareApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxBus.getInstance().toObserverable().filter(o -> o instanceof AuthError).map(o ->(AuthError) o)
                .subscribe(this::onAuthError);
    }

    private void onAuthError(AuthError error) {
        Intent startActivity = new Intent();
        startActivity.setClass(HealthcareApp.this, AuthActivity.class);
        startActivity.setAction(AuthActivity.class.getName());
        startActivity.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(startActivity);
    }

}
