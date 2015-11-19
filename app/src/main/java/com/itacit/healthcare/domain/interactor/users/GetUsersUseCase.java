package com.itacit.healthcare.domain.interactor.users;

import com.itacit.healthcare.data.entries.User;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.UsersService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;


/**
 * Created by root on 11.11.15.
 */
public class GetUsersUseCase extends GetListUseCase<User, String> {
    private final String SEARCH_PREFIX = "search:";

    @Override
    protected ListRequest initArgs(String query) {
        ListRequest requestBody = new ListRequest();
        requestBody.setQuery(SEARCH_PREFIX + query);
        return requestBody;
    }

    @Override
    protected Observable<ListResponse<User>> request(ListRequest requestBody) {
        return UsersService.getApi().getUsersList(requestBody);
    }
}
