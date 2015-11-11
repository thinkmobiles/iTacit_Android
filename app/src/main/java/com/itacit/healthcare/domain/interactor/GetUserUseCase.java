package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.request.ItemRequest;
import com.itacit.healthcare.data.network.services.UsersService;

import rx.Observable;

/**
 * Created by root on 11.11.15.
 */
public class GetUserUseCase extends UseCase {
    private ItemRequest request;

    public GetUserUseCase(int id) {
        this.request = new ItemRequest();
        request.setId(id);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return UsersService.getApi().getUser(request);
    }
}
