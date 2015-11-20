package com.itacit.healthcare.domain.interactor.groups;

import com.itacit.healthcare.data.entries.Business;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.GroupsService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by root on 13.11.15.
 */
public class GetBusinessUseCase extends GetListUseCase<Business, String> {
    private final String SEARCH_PREFIX = "search:";

    @Override
    protected ListRequest initArgs(String query) {
        ListRequest requestBody = new ListRequest();
        requestBody.setQuery(SEARCH_PREFIX + query);
        return requestBody;
    }

    @Override
    protected Observable<ListResponse<Business>> request(ListRequest requestBody) {
        return GroupsService.getApi().getBusinessUnits(requestBody);
    }
}
