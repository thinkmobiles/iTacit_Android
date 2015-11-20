package com.itacit.healthcare.domain.interactor.auth;

import com.itacit.healthcare.data.network.services.AuthService;
import com.itacit.healthcare.domain.interactor.UseCase;
import com.itacit.healthcare.domain.models.AuthParams;

import rx.Observable;

/**
 * Created by root on 28.10.15.
 */
public class LoginUseCase extends UseCase<Boolean, AuthParams, AuthParams> {

    @Override
    protected Observable<Boolean> buildUseCaseObservable(AuthParams authParams) {
        return AuthService.login(authParams.getUserName(), authParams.getPassword());
    }

    @Override
    protected AuthParams initArgs(AuthParams authParams) {
        return authParams;
    }
}
