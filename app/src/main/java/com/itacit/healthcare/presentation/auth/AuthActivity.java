package com.itacit.healthcare.presentation.auth;

import android.content.Intent;
import android.os.Bundle;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.LoginUseCase;
import com.itacit.healthcare.presentation.base.BaseActivity;
import com.itacit.healthcare.presentation.messages.MessagesActivity;
import com.itacit.healthcare.presentation.news.NewsActivity;

import rx.Subscriber;

/**
 * Created by root on 28.10.15.
 */
public class AuthActivity extends BaseActivity {
    @Override
    protected int getLayoutRes() {
        return R.layout.acitivity_auth;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginUseCase loginUseCase = new LoginUseCase("ph", "ph");
        loginUseCase.execute(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean success) {
                startActivity(new Intent(AuthActivity.this, MessagesActivity.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
