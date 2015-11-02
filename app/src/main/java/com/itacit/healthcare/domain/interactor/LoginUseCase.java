package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.AuthManager;

import rx.Observable;

/**
 * Created by root on 28.10.15.
 */
public class LoginUseCase extends UseCase {

    private String userName;
    private String password;

    public LoginUseCase(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return AuthManager.login(userName, password);
    }
}
