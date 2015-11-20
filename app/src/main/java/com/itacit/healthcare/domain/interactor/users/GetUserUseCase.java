package com.itacit.healthcare.domain.interactor.users;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.data.network.request.ItemRequest;
import com.itacit.healthcare.data.network.services.UsersService;
import com.itacit.healthcare.domain.interactor.GetItemUseCase;

import rx.Observable;

/**
 * Created by root on 11.11.15.
 */
public class GetUserUseCase extends GetItemUseCase<User, String> {
    @Override
    protected Observable<User> buildUseCaseObservable(ItemRequest requestBody) {
        return UsersService.getApi().getUser(requestBody);
    }

    @Override
    protected ItemRequest initArgs(String id) {
        ItemRequest requestBody = new ItemRequest();
        requestBody.setId(id);
        return requestBody;
    }
}
