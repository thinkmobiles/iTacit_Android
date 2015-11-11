package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.data.network.services.UsersService;

import rx.Observable;

/**
 * Created by root on 11.11.15.
 */
public class GetUserUseCase extends GetItemUseCase<User> {

    public GetUserUseCase(int id) {
        super(id);
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return UsersService.getApi().getUser(request);
    }
}
