package com.itacit.healthcare.domain.interactor.groups;

import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.GroupsService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by root on 13.11.15.
 */
public class GetPermissionGroupsUseCase extends GetListUseCase<Object> {


    public GetPermissionGroupsUseCase(Integer startIndex, Integer rowCounts) {
        super(null, null);
    }

    @Override
    protected Observable<ListResponse<Object>> request() {
        return GroupsService.getApi().getPermissionGroups(requestBody);
    }
}
