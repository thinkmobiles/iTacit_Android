package com.itacit.healthcare.domain.interactor.groups;

import com.itacit.healthcare.data.entries.BusinessUnit;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.GroupsService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by root on 13.11.15.
 */
public class GetBusinessUnitsUseCase extends GetListUseCase<BusinessUnit> {
    public GetBusinessUnitsUseCase() {
        super(null, null);
    }

    @Override
    protected Observable<ListResponse<BusinessUnit>> request() {
        return GroupsService.getApi().getBusinessUnits(requestBody);
    }
}
