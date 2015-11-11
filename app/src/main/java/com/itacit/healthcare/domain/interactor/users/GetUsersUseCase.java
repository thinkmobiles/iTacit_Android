package com.itacit.healthcare.domain.interactor.users;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.UsersService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by root on 11.11.15.
 */
public class GetUsersUseCase extends GetListUseCase<User> {
    public GetUsersUseCase(int startIndex, int rowCounts) {
        super(startIndex, rowCounts);
    }

    @Override
    protected Observable<ListResponse<User>> request() {
        return UsersService.getApi().getUsersList(listRequest);
    }
}
